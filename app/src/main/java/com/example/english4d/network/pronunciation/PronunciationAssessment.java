package com.example.english4d.network.pronunciation;

import android.util.Log;

import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGradingSystem;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGranularity;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult;
import com.microsoft.cognitiveservices.speech.PropertyId;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioProcessingConstants;
import com.microsoft.cognitiveservices.speech.audio.AudioProcessingOptions;

public class PronunciationAssessment {
    private SpeechConfig speechConfig = SpeechConfig.fromSubscription(KeyApi.SpeechSubscriptionKey, KeyApi.SpeechRegion);
    private MicrophoneStream microphoneStream;
    private RecordingListener recordingListener;


    private String filePath;
    public void setReferenceText(String referenceText ) {
        if (referenceText == null){
            this.referenceText = "";
        }else {
            this.referenceText = referenceText;
        }
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String referenceText = "";

    private MicrophoneStream createMicrophoneStream() {
        this.releaseMicrophoneStream();

        microphoneStream = new MicrophoneStream(filePath);
        return microphoneStream;
    }

    private void releaseMicrophoneStream() {
        if (microphoneStream != null) {
            microphoneStream.close();
            microphoneStream = null;
        }
    }
    public void setRecordingListener(RecordingListener listener) {
        this.recordingListener = listener; // Thiết lập listener
    }
    public void startRecord() {
        if (this.microphoneStream != null) {
                /*
                 The pronunciation assessment service has a longer default end silence timeout (5 seconds) than normal STT
                 as the pronunciation assessment is widely used in education scenario where kids have longer break in reading.
                 You can explicitly stop the recording stream to stop assessment and the service will return the results immediately
                 */
            this.releaseMicrophoneStream();
            return;
        }
        recordingListener.onRecordingStarted();
        try {

            createMicrophoneStream();
            AudioProcessingOptions audioProcessingOptions = AudioProcessingOptions.create(
                    AudioProcessingConstants.AUDIO_INPUT_PROCESSING_DISABLE_ECHO_CANCELLATION |
                            AudioProcessingConstants.AUDIO_INPUT_PROCESSING_DISABLE_NOISE_SUPPRESSION
            );
            final AudioConfig audioConfig = AudioConfig.fromStreamInput(createMicrophoneStream(),audioProcessingOptions);
            audioConfig.setProperty(PropertyId.SpeechServiceConnection_EndSilenceTimeoutMs, "1500");
            audioConfig.setProperty(PropertyId.SpeechServiceConnection_InitialSilenceTimeoutMs, "2000");
            // Switch to other languages for example Spanish, change language "en-US" to "es-ES". Language name is not case sensitive.
            final SpeechRecognizer reco = new SpeechRecognizer(speechConfig, "en-US", audioConfig);

            PronunciationAssessmentConfig pronConfig =
                    new PronunciationAssessmentConfig(referenceText,
                            PronunciationAssessmentGradingSystem.HundredMark,
                            PronunciationAssessmentGranularity.Phoneme);

            pronConfig.applyTo(reco);
            reco.recognizing.addEventListener((o, speechRecognitionResultEventArgs)->{
                recordingListener.onProcess();
            });
            reco.recognized.addEventListener((o, speechRecognitionResultEventArgs) -> {
                PronunciationAssessmentResult pronResult = PronunciationAssessmentResult.fromResult(speechRecognitionResultEventArgs.getResult());
                if (pronResult != null) {
                    recordingListener.onResultReceived(new PronunciationResult(
                            pronResult.getAccuracyScore(), pronResult.getPronunciationScore(), pronResult.getCompletenessScore(), pronResult.getFluencyScore()
                    ));
                }
                else {
                    recordingListener.onResultReceived(null);
                }
            });

            reco.sessionStopped.addEventListener((o, s) -> {
                reco.stopContinuousRecognitionAsync();

                this.releaseMicrophoneStream();
            });

            reco.recognizeOnceAsync();
        } catch (Exception ex) {
            Log.e("ERROR",ex.getMessage());
        }
    }

    public interface RecordingListener {
        void onRecordingStarted(); // Được gọi khi bắt đầu ghi âm
        void onProcess();

        void onResultReceived(PronunciationResult pronunciationResult); // Khi nhận được kết quả
    }
}
