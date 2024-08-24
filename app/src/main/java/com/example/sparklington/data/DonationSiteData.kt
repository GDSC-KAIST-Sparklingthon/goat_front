package com.example.sparklington.data

import com.example.sparklington.DonationSite
import com.example.sparklington.R

data class DonationSiteData(
    val krName: String,
    val engName: String,
    val url: String,
    val imageResId: Int,
    val description: String,
    val category: String
)//

// DonationSite 리스트

// DonationSite 리스트를 반환하는 함수
fun getDonationSites(): List<DonationSite> {
    return listOf(
        DonationSite(
            "한국컴패션",
            "COMPASSION",
            "https://www.compassionkorea.org",
            R.drawable.compassion_logo,
            R.drawable.compassion,
            "한국의 전쟁고아를 돕기 위해 시작한 컴패션은 현재 전세계 29개국의 가난한 어린이들을 후원하여 자립이 가능 성인이 될 때 까지 전인적으로 양육하는 국제어린이양육기구입니다.",
            "아동"
        ),
        DonationSite(
            "적십자사",
            "Red Cross",
            "https://www.redcross.org",
            R.drawable.redcross_logo,
            R.drawable.redcross,
            "재난 구호, 생명 구조 활동",
            "보건"
        ),
        DonationSite(
            "유니세프",
            "UNICEF",
            "https://www.unicef.org",
            R.drawable.unicef_logo,
            R.drawable.unicef,
            "세계 아동 권리 보호 및 복지 활동",
            "아동"
        ),
        DonationSite(
            "그린피스",
            "Green Peace",
            "https://www.greenpeace.org",
            R.drawable.greenpeace_logo,
            R.drawable.greenpeace,
            "그린피스는 1971년 태어난 독립적인 국제환경단체로 지구 환경보호와 평화를 위해 비폭력 직접행동의 평화적인 방식으로 캠페인을 진행하고 있습니다.",
            "환경"
        ),
        DonationSite(
            "월드비전",
            "World Vision",
            "https://www.worldvision.org",
            R.drawable.worldvision_logo,
            R.drawable.worldvision,
            "세계적인 구호 개발 기구로서 전 세계 아동, 가정 및 지역사회를 돕습니다.",
            "아동"
        ),
        DonationSite(
            "세이브더칠드런",
            "Save the Children",
            "https://www.sc.or.kr",
            R.drawable.save_the_childeren_logo,
            R.drawable.save_the_children,
            "전 세계 아동의 권리를 보호하고, 그들이 건강하게 성장할 수 있도록 돕기 위해 활동하는 국제 비영리 단체입니다.",
            "아동"
        )
    )
}