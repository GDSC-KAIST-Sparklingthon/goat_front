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
            R.drawable.grass,
            "한국의 전쟁고아를 돕기 위해 시작한 컴패션은 현재 전세계 29개국의 가난한 어린이들을 후원하여 자립이 가능 성인이 될 때 까지 전인적으로 양육하는 국제어린이양육기구입니다.",
            "아동"
        ),
        DonationSite(
            "적십자사",
            "Red Cross",
            "https://www.redcross.org",
            R.drawable.grass,
            "재난 구호, 생명 구조 활동",
            "보건"
        ),
        DonationSite(
            "유니세프",
            "UNICEF",
            "https://www.unicef.org",
            R.drawable.grass,
            "세계 아동 권리 보호 및 복지 활동",
            "아동"
        ),
        DonationSite(
            "그린피스",
            "Green Peace",
            "https://www.greenpeace.org",
            R.drawable.grass,
            "국제 환경 보호 단체로서 환경 문제 해결을 위한 활동을 합니다.",
            "환경"
        ),
        DonationSite(
            "월드비전",
            "World Vision",
            "https://www.worldvision.org",
            R.drawable.grass,
            "세계적인 구호 개발 기구로서 전 세계 아동, 가정 및 지역사회를 돕습니다.",
            "아동"
        )
    )
}