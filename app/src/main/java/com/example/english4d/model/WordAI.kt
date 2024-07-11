package com.example.english4d.model
import com.example.english4d.BuildConfig

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig

val model2 = GenerativeModel(
    "gemini-1.0-pro",
    BuildConfig.apiGemini2,
    generationConfig = generationConfig {
        temperature = 0.9f
        topK = 0
        topP = 1f
        maxOutputTokens = 2048
    },
    // safetySettings = Adjust safety settings
    // See https://ai.google.dev/gemini-api/docs/safety-settings
)

suspend fun ResponseData(word : String): String {
    return model2.generateContent(
        content() {
            text("input: play")
            text("output: {\n  \"entry\": \"play\",\n  \"response\": \"play\",\n  \"vietnamese\": \"chơi\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/pleɪ/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"verb\",\n        \"definition_en\": \"Engage in activity for enjoyment and recreation.\",\n        \"definition_vi\": \"Tham gia vào hoạt động vui chơi và giải trí.\",\n        \"example\": {\n          \"example_en\": \"She loves to play with her toys.\",\n          \"example_vi\": \"Cô ấy rất thích chơi với đồ chơi của mình.\"\n        }\n      }\n    ],\n    \"antonyms\": [\n      {\n        \"word\": \"work\",\n        \"phonetic\": \"/wɜːrk/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"verb\",\n            \"definition_en\": \"Engage in activity involving mental or physical effort done in order to achieve a purpose or result.\",\n            \"definition_vi\": \"Tham gia vào hoạt động đòi hỏi nỗ lực về tinh thần hoặc vật chất nhằm đạt được mục đích hoặc kết quả.\",\n            \"example\": {\n              \"example_en\": \"She works in an office.\",\n              \"example_vi\": \"Cô ấy làm việc trong văn phòng.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"study\",\n        \"phonetic\": \"/ˈstʌdi/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"verb\",\n            \"definition_en\": \"Apply oneself to learning a particular subject or branch of learning, especially with formal preparation for an examination.\",\n            \"definition_vi\": \"Tập trung học hành một môn học cụ thể hoặc một lĩnh vực học, đặc biệt là chuẩn bị hình thức cho kỳ thi.\",\n            \"example\": {\n              \"example_en\": \"She studies hard for her exams.\",\n              \"example_vi\": \"Cô ấy học hành chăm chỉ cho kỳ thi của mình.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"behave\",\n        \"phonetic\": \"/bɪˈheɪv/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"verb\",\n            \"definition_en\": \"Act or conduct oneself in a specified way, especially towards others.\",\n            \"definition_vi\": \"Hành xử theo cách được chỉ định, đặc biệt là đối với người khác.\",\n            \"example\": {\n              \"example_en\": \"Children should learn to behave in public places.\",\n              \"example_vi\": \"Trẻ em nên học cách cư xử trong các nơi công cộng.\"\n            }\n          }\n        ]\n      }\n    ]\n  }\n}")
            text("input: pla")
            text("output: {\n  \"entry\": \"pla\",\n  \"response\": \"play\",\n  \"vietnamese\": \"chơi\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/pleɪ/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"verb\",\n        \"definition_en\": \"Engage in activity for enjoyment and recreation.\",\n        \"definition_vi\": \"Tham gia vào hoạt động vui chơi và giải trí.\",\n        \"example\": {\n          \"example_en\": \"She loves to play with her toys.\",\n          \"example_vi\": \"Cô ấy rất thích chơi với đồ chơi của mình.\"\n        }\n      }\n    ],\n    \"antonyms\": [\n      {\n        \"word\": \"work\",\n        \"phonetic\": \"/wɜːrk/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"verb\",\n            \"definition_en\": \"Engage in activity involving mental or physical effort done in order to achieve a purpose or result.\",\n            \"definition_vi\": \"Tham gia vào hoạt động đòi hỏi nỗ lực về tinh thần hoặc vật chất nhằm đạt được mục đích hoặc kết quả.\",\n            \"example\": {\n              \"example_en\": \"She works in an office.\",\n              \"example_vi\": \"Cô ấy làm việc trong văn phòng.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"study\",\n        \"phonetic\": \"/ˈstʌdi/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"verb\",\n            \"definition_en\": \"Apply oneself to learning a particular subject or branch of learning, especially with formal preparation for an examination.\",\n            \"definition_vi\": \"Tập trung học hành một môn học cụ thể hoặc một lĩnh vực học, đặc biệt là chuẩn bị hình thức cho kỳ thi.\",\n            \"example\": {\n              \"example_en\": \"She studies hard for her exams.\",\n              \"example_vi\": \"Cô ấy học hành chăm chỉ cho kỳ thi của mình.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"behave\",\n        \"phonetic\": \"/bɪˈheɪv/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"verb\",\n            \"definition_en\": \"Act or conduct oneself in a specified way, especially towards others.\",\n            \"definition_vi\": \"Hành xử theo cách được chỉ định, đặc biệt là đối với người khác.\",\n            \"example\": {\n              \"example_en\": \"Children should learn to behave in public places.\",\n              \"example_vi\": \"Trẻ em nên học cách cư xử trong các nơi công cộng.\"\n            }\n          }\n        ]\n      }\n    ]\n  }\n}")
            text("input: orange")
            text("output: {\n  \"entry\": \"orange\",\n  \"response\": \"orange\",\n  \"vietnamese\": \"quả cam\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/ˈɒrɪndʒ/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"noun\",\n        \"definition_en\": \"A round juicy citrus fruit with a tough bright reddish-yellow rind.\",\n        \"definition_vi\": \"Một loại trái cây có vỏ ngoài màu cam sáng, tròn và nhiều nước.\",\n        \"example\": {\n          \"example_en\": \"She peeled an orange and shared it with her friends.\",\n          \"example_vi\": \"Cô ấy gọt một quả cam và chia sẻ nó với bạn bè.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"noun\",\n        \"definition_en\": \"A bright reddish-yellow color.\",\n        \"definition_vi\": \"Màu cam .\",\n        \"example\": {\n          \"example_en\": \"The sunset painted the sky with hues of orange.\",\n          \"example_vi\": \"Hoàng hôn nhuộm bầu trời với những sắc cam.\"\n        }\n      }\n    ],\n    \"antonyms\": []\n  }\n}")
            text("input: phone")
            text("output: {\n  \"entry\": \"phone\",\n  \"response\": \"phone\",\n  \"vietnamese\": \"điện thoại\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/foʊn/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"noun\",\n        \"definition_en\": \"A device that converts sound, typically of voice or music, into electrical signals for transmission by wire or radio.\",\n        \"definition_vi\": \"Một thiết bị chuyển đổi âm thanh, thường là giọng nói hoặc nhạc, thành tín hiệu điện để truyền qua dây hoặc sóng vô tuyến.\",\n        \"example\": {\n          \"example_en\": \"He forgot his phone at home.\",\n          \"example_vi\": \"Anh ấy quên điện thoại ở nhà.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"verb\",\n        \"definition_en\": \"To make a telephone call to someone.\",\n        \"definition_vi\": \"Gọi điện thoại cho ai đó.\",\n        \"example\": {\n          \"example_en\": \"He will phone his parents tonight.\",\n          \"example_vi\": \"Anh ấy sẽ gọi điện cho bố mẹ vào tối nay.\"\n        }\n      }\n    ],\n    \"antonyms\": []\n  }\n}")
            text("input: phon")
            text("output: {\n  \"entry\": \"phon\",\n  \"response\": \"phone\",\n  \"vietnamese\": \"điện thoại\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/foʊn/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"noun\",\n        \"definition_en\": \"A device that converts sound, typically of voice or music, into electrical signals for transmission by wire or radio.\",\n        \"definition_vi\": \"Một thiết bị chuyển đổi âm thanh, thường là giọng nói hoặc nhạc, thành tín hiệu điện để truyền qua dây hoặc sóng vô tuyến.\",\n        \"example\": {\n          \"example_en\": \"He forgot his phone at home.\",\n          \"example_vi\": \"Anh ấy quên điện thoại ở nhà.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"verb\",\n        \"definition_en\": \"To make a telephone call to someone.\",\n        \"definition_vi\": \"Gọi điện thoại cho ai đó.\",\n        \"example\": {\n          \"example_en\": \"He will phone his parents tonight.\",\n          \"example_vi\": \"Anh ấy sẽ gọi điện cho bố mẹ vào tối nay.\"\n        }\n      }\n    ],\n    \"antonyms\": []\n  }\n}")
            text("input: high")
            text("output: {\n  \"entry\": \"high\",\n  \"response\": \"high\",\n  \"vietnamese\": \"cao\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/haɪ/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"adjective\",\n        \"definition_en\": \"Of great vertical extent; lofty; tall.\",\n        \"definition_vi\": \"Cao, có độ cao lớn.\",\n        \"example\": {\n          \"example_en\": \"The mountains are very high.\",\n          \"example_vi\": \"Những ngọn núi rất cao.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"adjective\",\n        \"definition_en\": \"Extending far upward; having a specified height when measured from base to top.\",\n        \"definition_vi\": \"Mở rộng lên cao; có chiều cao cụ thể khi được đo từ đáy đến đỉnh.\",\n        \"example\": {\n          \"example_en\": \"The ceiling is 3 meters high.\",\n          \"example_vi\": \"Trần nhà cao 3 mét.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"adjective\",\n        \"definition_en\": \"Situated far above the ground, sea level, or another point of reference.\",\n        \"definition_vi\": \"Được đặt cao hơn đất, mực nước biển hoặc một điểm tham chiếu khác.\",\n        \"example\": {\n          \"example_en\": \"The airplane flew at a high altitude.\",\n          \"example_vi\": \"Máy bay bay ở độ cao cao.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"adverb\",\n        \"definition_en\": \"At or to a considerable or specified height.\",\n        \"definition_vi\": \"Ở hoặc tới một chiều cao đáng kể hoặc cụ thể.\",\n        \"example\": {\n          \"example_en\": \"They jumped high to catch the ball.\",\n          \"example_vi\": \"Họ nhảy cao để bắt trái bóng.\"\n        }\n      }\n    ],\n    \"antonyms\": [\n      {\n        \"word\": \"low\",\n        \"phonetic\": \"/loʊ/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"adjective\",\n            \"definition_en\": \"Of less than average height from top to bottom or to the top from the ground.\",\n            \"definition_vi\": \"Có chiều cao thấp hơn trung bình từ trên xuống hoặc đến đỉnh từ mặt đất.\",\n            \"example\": {\n              \"example_en\": \"The table is low enough for children to reach easily.\",\n              \"example_vi\": \"Bàn thấp đủ để trẻ em dễ dàng tiếp cận.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"short\",\n        \"phonetic\": \"/ʃɔːrt/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"adjective\",\n            \"definition_en\": \"Measuring a small distance from end to end.\",\n            \"definition_vi\": \"Đo khoảng cách nhỏ từ đầu đến cuối.\",\n            \"example\": {\n              \"example_en\": \"She is too short to reach the top shelf.\",\n              \"example_vi\": \"Cô ấy quá ngắn để đến giá trên cùng.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"deep\",\n        \"phonetic\": \"/diːp/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"adjective\",\n            \"definition_en\": \"Extending far downward from the top or surface.\",\n            \"definition_vi\": \"Mở rộng xuống xa từ đỉnh hoặc mặt phẳng.\",\n            \"example\": {\n              \"example_en\": \"The lake is very deep.\",\n              \"example_vi\": \"Hồ rất sâu.\"\n            }\n          }\n        ]\n      }\n    ]\n  }\n}")
            text("input: hig")
            text("output: {\n  \"entry\": \"hig\",\n  \"response\": \"high\",\n  \"vietnamese\": \"cao\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/haɪ/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"adjective\",\n        \"definition_en\": \"Of great vertical extent; lofty; tall.\",\n        \"definition_vi\": \"Cao, có độ cao lớn.\",\n        \"example\": {\n          \"example_en\": \"The mountains are very high.\",\n          \"example_vi\": \"Những ngọn núi rất cao.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"adjective\",\n        \"definition_en\": \"Extending far upward; having a specified height when measured from base to top.\",\n        \"definition_vi\": \"Mở rộng lên cao; có chiều cao cụ thể khi được đo từ đáy đến đỉnh.\",\n        \"example\": {\n          \"example_en\": \"The ceiling is 3 meters high.\",\n          \"example_vi\": \"Trần nhà cao 3 mét.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"adjective\",\n        \"definition_en\": \"Situated far above the ground, sea level, or another point of reference.\",\n        \"definition_vi\": \"Được đặt cao hơn đất, mực nước biển hoặc một điểm tham chiếu khác.\",\n        \"example\": {\n          \"example_en\": \"The airplane flew at a high altitude.\",\n          \"example_vi\": \"Máy bay bay ở độ cao cao.\"\n        }\n      },\n      {\n        \"partOfSpeech\": \"adverb\",\n        \"definition_en\": \"At or to a considerable or specified height.\",\n        \"definition_vi\": \"Ở hoặc tới một chiều cao đáng kể hoặc cụ thể.\",\n        \"example\": {\n          \"example_en\": \"They jumped high to catch the ball.\",\n          \"example_vi\": \"Họ nhảy cao để bắt trái bóng.\"\n        }\n      }\n    ],\n    \"antonyms\": [\n      {\n        \"word\": \"low\",\n        \"phonetic\": \"/loʊ/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"adjective\",\n            \"definition_en\": \"Of less than average height from top to bottom or to the top from the ground.\",\n            \"definition_vi\": \"Có chiều cao thấp hơn trung bình từ trên xuống hoặc đến đỉnh từ mặt đất.\",\n            \"example\": {\n              \"example_en\": \"The table is low enough for children to reach easily.\",\n              \"example_vi\": \"Bàn thấp đủ để trẻ em dễ dàng tiếp cận.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"short\",\n        \"phonetic\": \"/ʃɔːrt/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"adjective\",\n            \"definition_en\": \"Measuring a small distance from end to end.\",\n            \"definition_vi\": \"Đo khoảng cách nhỏ từ đầu đến cuối.\",\n            \"example\": {\n              \"example_en\": \"She is too short to reach the top shelf.\",\n              \"example_vi\": \"Cô ấy quá ngắn để đến giá trên cùng.\"\n            }\n          }\n        ]\n      },\n      {\n        \"word\": \"deep\",\n        \"phonetic\": \"/diːp/\",\n        \"definitions\": [\n          {\n            \"partOfSpeech\": \"adjective\",\n            \"definition_en\": \"Extending far downward from the top or surface.\",\n            \"definition_vi\": \"Mở rộng xuống xa từ đỉnh hoặc mặt phẳng.\",\n            \"example\": {\n              \"example_en\": \"The lake is very deep.\",\n              \"example_vi\": \"Hồ rất sâu.\"\n            }\n          }\n        ]\n      }\n    ]\n  }\n}")
            text("input: musi")
            text("output: {\n  \"entry\": \"musi\",\n  \"response\": \"music\",\n  \"vietnamese\": \"âm nhạc\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/ˈmjuːzɪk/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"noun\",\n        \"definition_en\": \"An art form whose medium is sound organized in time.\",\n        \"definition_vi\": \"Một hình thức nghệ thuật có phương tiện là âm thanh được tổ chức theo thời gian.\",\n        \"example\": {\n          \"example_en\": \"He loves to listen to music.\",\n          \"example_vi\": \"Anh ấy rất thích nghe nhạc.\"\n        }\n      }\n    ],\n    \"antonyms\": []\n  }\n}")
            text("input: music")
            text("output: {\n  \"entry\": \"music\",\n  \"response\": \"music\",\n  \"vietnamese\": \"âm nhạc\",\n  \"status\": \"success\",\n  \"data\": {\n    \"phonetic\": \"/ˈmjuːzɪk/\",\n    \"definitions\": [\n      {\n        \"partOfSpeech\": \"noun\",\n        \"definition_en\": \"An art form whose medium is sound organized in time.\",\n        \"definition_vi\": \"Một hình thức nghệ thuật có phương tiện là âm thanh được tổ chức theo thời gian.\",\n        \"example\": {\n          \"example_en\": \"He loves to listen to music.\",\n          \"example_vi\": \"Anh ấy rất thích nghe nhạc.\"\n        }\n      }\n    ],\n    \"antonyms\": []\n  }\n}")
            text("input: $word")
            text("output: ")
        }
    ).text.toString()
}


