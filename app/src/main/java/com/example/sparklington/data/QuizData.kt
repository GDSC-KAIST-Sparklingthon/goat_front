package com.example.sparklington.data

import kotlin.random.Random

data class Quiz (
    val question:String,
    val answer: Boolean
)
fun <T> getRandomElement(list: List<T>): T {
    return list[Random.nextInt(list.size)]
}
object QuizData {
    val quizList = listOf(
        Quiz("UN 지속가능발전목표(SDGs) 중 목표 1의 구체적 세부 목표는 2030년까지 하루 1.90달러 미만으로 생활하는 모든 사람을 제거하는 것이다.",
            true),
        Quiz("목표 5는 성평등을 달성하고 모든 여성과 소녀를 권한화하는 것인데, 이 목표에는 조혼과 여성 생식 건강과 관련된 권리에 대한 접근을 보장하는 내용이 포함되어 있다.",
            true),
        Quiz("지속가능발전목표 7은 전 세계적으로 지속 가능하고 현대적인 에너지를 보장하는 것을 목표로 하는데, 이 목표에는 재생 가능 에너지 비율을 2030년까지 50%로 높이는 것이 포함되어 있다.",
            false),
        Quiz("목표 10은 불평등을 줄이는 것이며, 여기에는 2030년까지 전 세계적으로 소득 불평등을 절반으로 줄이는 세부 목표가 있다.",
            false),
        Quiz("지속가능발전목표 13은 기후 행동에 관한 목표로, 국가 간 기후 변화에 대응하기 위한 법적 구속력 있는 협정을 2020년까지 체결하는 것이 포함되어 있다.",
            false),
        Quiz("목표 14는 해양 생태계 보존과 관련된 목표로, 2025년까지 모든 연안과 해양 지역에서 해양 쓰레기를 80%까지 줄이는 세부 목표가 포함되어 있다.",
            false),
        Quiz("목표 11은 지속 가능한 도시와 공동체에 대한 목표로, 2030년까지 모든 사람에게 적절하고 안전하며 저렴한 주거를 제공하는 세부 목표가 포함되어 있다.",
            true),
        Quiz("지속가능발전목표 16은 평화, 정의 및 강력한 제도에 관한 목표로, 여기에는 모든 형태의 폭력을 줄이고 사법 접근성을 강화하는 내용이 포함되어 있다.",
            true),
        Quiz("목표 9는 산업, 혁신 및 기반 시설에 관한 목표로, 2030년까지 모든 국가에서 인프라 개선을 통해 경제 성장을 7%로 유지하는 세부 목표가 있다.",
            false),
        Quiz("지속가능발전목표 15는 육상 생태계 보존에 관한 목표로, 2020년까지 세계의 산림 면적을 50% 이상 보호구역으로 지정하는 것이 포함되어 있다.",
            false),
    )
    fun randomQuiz():Quiz = getRandomElement(quizList)
}