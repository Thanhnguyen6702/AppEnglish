package com.example.english4d.network.pronunciation;

public class PronunciationResult {
    private final double accuracyScore;
    private final double pronunciationScore;
    private final double completenessScore;
    private final double fluencyScore;

    public PronunciationResult(double accuracyScore, double pronunciationScore, double completenessScore, double fluencyScore) {
        this.accuracyScore = accuracyScore;
        this.pronunciationScore = pronunciationScore;
        this.completenessScore = completenessScore;
        this.fluencyScore = fluencyScore;
    }

    public double getAccuracyScore() {
        return accuracyScore;
    }

    public double getPronunciationScore() {
        return pronunciationScore;
    }

    public double getCompletenessScore() {
        return completenessScore;
    }

    public double getFluencyScore() {
        return fluencyScore;
    }

}

