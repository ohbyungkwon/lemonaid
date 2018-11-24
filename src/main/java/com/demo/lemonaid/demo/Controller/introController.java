package com.demo.lemonaid.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class introController {
    @GetMapping("/disease")
    @ResponseBody
    public Map<String, Object> disease(){
        Map<String, Object> map = new HashMap<>();
        String temp[] = {
         "탈모",
         "발기부전"
        };
        map.put("disease",temp);
        return map;
    }

    @GetMapping("/intro")
    @ResponseBody
    public Map<String, Object> disease_choice(@RequestParam String disease_name){
        Map<String, Object> map = new HashMap<>();
        map.put("name", disease_name);
        if(disease_name.equals("발기부전")) {
            String []arr = new String[11];
            arr[0] = "만 30세 미만, 67세 이상";
            arr[1] = "여성";
            arr[2] = "지난 5년간 생식기를 포함한 신체검사를 받지 않은 자";
            arr[3] = "어떤 이유에서든 성관계가 권장되지 않는 경우";
            arr[4] = "니트로글리세린이나 질산염이 포함된 약 복용";
            arr[5] = "혈압을 모르거나 저혈압, 이유 없이 기절이나 어지러움을 경험";
            arr[6] = "신장 질환자";
            arr[7] = "간 질환자";
            arr[8] = "HIV";
            arr[9] = "페이로니 질병을 포함한 성기의 신체적 기형";
            arr[10] = "심장 질환, 뇌 질환, 혹은 다른 심혈관 질환자";

            map.put("medicineName", "발기부전제");
            map.put("intro", "온라인 영상 진료로 간단하게 진단받고 발기부전제 처방 받아 원하는 약국에서 픽업하세요! 발기부전제 원격 처방 서비스는 30~66세 남성만을 대상으로 하고 있습니다.");
            map.put("guideExam","브랜드/복제약 선호 여부, 혈압을 포함한 건강 문진에 응답합니다.");
            map.put("guidePay","회원가입 및 로그인 후 진료비 3000원을 결제합니다.");
            map.put("guidePresc","결제 후 2시간 내로 의료진과 비디오 콜을 통한 영상진료를 진행합니다.");
            map.put("guidePick","처방전이 발급되면 원하는 약국을 선택하여 해당 약국에서 약을 픽업합니다.");
            map.put("define","발기부전은 성생활에 충분한 발기가 되지 않거나 유지되지 않는 상태를 의미합니다. 일반적으로" +
                    "이러한 상태가 3개월 이상 지속되었을 경우 발기부전으로 정의합니다." +
                    "발기부전 환자는 지속적으로 증가하는 추세를 보이고 있으며, 우리나라 40대 이상 남성 중 52%가" +
                    "발기에 문제가 있다는 조사 결과도 있습니다." +
                    "발기부전은 신체적, 심리적 원인으로 인해 발병할 수 있습니다. 신체적 원인으로는 고령, 흡연, 음주," +
                    "당뇨, 뇌혈관질환 등이 있습니다. 호르몬제제, 고혈압 치료제 중 일부, 항정신성 약물 등도 발기부전의" +
                    "원인이 되곤 합니다. 한편, 정서적 스트레스, 우울증이나 불안장애 등 심리적 요인도 발기부전의" +
                    "원인이 될 수 있습니다.");
            map.put("treatAndRisk","본 원격 처방 서비스에서 처방하는 발기부전제는 일반적으로 안전하지만 유희용으로 복용되어서는" +
                    "안됩니다. 처방되는 의약품은 질병 치료를 위해 제조되었으며, 부작용으로는 4시간 이상 지속되는" +
                    "발기, 갑작스러운 실명, 난청 및 청력 손실을 유발할 수 있습니다." +
                    "저혈압, 혹은 심장질환을 가지고 있거나, 심장질환이나 고혈압 약을 복용하고 있는 분에게는" +
                    "발기부전제 원격 처방 서비스가 적절하지 않습니다. 그럼에도 불구하고 본 서비스를 이용하여" +
                    "발기부전제를 처방받아 복용할 시, 극심한 어지러움증, 기절, 심장마비, 그리고 뇌졸중과 같은 심각한" +
                    "부작용을 경험할 수 있습니다. 발기부전제 의약품과 관련된 전체 부작용 리스트는 발기부전제 복약" +
                    "안내문을 참고하세요.");
            map.put("comment","다음 항목 중 하나라도 해당된다면 발기부전 원격 처방 서비스에 적합하지 않으며 서비스 제외대상으로 구분 됩니다.");
            map.put("exceptService",arr);
            map.put("guideStart","지금부터 발기부전제 처방을 위한 문진을 시작합니다. 본 문진은 총 30개의 질문을 포함하고 있습니다." +
                    "\n" +
                    "문진을 제출한 뒤에는 응답을 수정할 수 없으니 신중하게 임해 주시길 바랍니다." +
                    "나아가 본 문진에서는 환자의 혈압을 묻는 질문을 포함하고 있습니다. 혈압은 가정용 혈압계 혹은" +
                    "가까운 병원이나 보건소에서 측정할 수 있습니다." +
                    "문진을 완료했다고 하여 발기부전제 원격 처방이 반드시 이루어지는 것은 아니며, 의료진이 환자의" +
                    "상태가 원격 처방에 적합하다고 판단할 경우에만 원격 처방이 이루어집니다. 약 처방전을 받지" +
                    "못하셨을 경우 진료비 3,000원을 전액 환불해 드립니다.");
        }

        else if(disease_name.equals("탈모")) {
            String []arr = new String[9];
            arr[0] = "만 18세 이하";
            arr[1] = "여성";
            arr[2] = "남성형 탈모가 아닌 다른 원인으로 인한 탈모";
            arr[3] = "피나스테리드에 대한 알레르기";
            arr[4] = "이미 다른 형식의 피나스테리드를 복용";
            arr[5] = "간 손상";
            arr[6] = "배뇨활동의 어려움";
            arr[7] = "전립선 암을 앓고 있거나 병력이 있음";
            arr[8] = "유방암을 앓고 있거나 병력이 있음";

            map.put("medicineName", "탈모약");
            map.put("intro", "온라인 영상 진료로 간편하게 진단받고 탈모약 처방 받아 원하는 약국에서 픽업하세요! 탈모약 원격 처방 서비스는 남성형 탈모 환자만을 대상으로 제공하고 있습니다.");
            map.put("guideExam","병력, 증상, 현재 복용 의약품, 알레르기 등과 관련된 건당문진에 응답하고 의료진이 진단할 탈모 부위를 촬영합니다.");
            map.put("guidePay","회원가입 및 로그인 후 진료비 3000원을 결제합니다.");
            map.put("guidePresc","결제 후 2시간 내로 의료진과 비디오 콜을 통한 영상진료를 진행합니다.");
            map.put("guidePick","처방전이 발급되면 원하는 약국을 선택하여 해당 약국에서 약을 픽업합니다.");
            map.put("define","탈모는 정상적으로 모발이 존재해야 할 부위에 모발이 없는 상태를 말하며, 일반적으로 두피의 굵고" +
                    "검은 머리털이 빠지는 것을 의미합니다. 우리나라 사람의 경우 하루에 약 50 - 70개의 머리카락이" +
                    "빠지는 것은 정상적인 현상입니다. 하지만 자고 나서나 머리를 감을 때 바지는 머리카락의 수가" +
                    "100개가 넘으면 병적인 원인에 의한 것일 가능성이 높으므로 의사와 상담해 보는 것이 좋습니다." +
                    "탈모는 흉터가 형성되는 것과 형성되지 않는 두 종류로 나눌 수 있습니다. 흉터가 형성되는 탈모는" +
                    "모낭이 파괴되므로 모발의 재생이 되지 않는 반면, 흉터가 형성되지 않는 탈모는 모낭이 유지되므로" +
                    "증상 부위가 사라진 후에 모발이 재생됩니다. 흉터가 형성되지 않는 탈모 중 하나가 유전성 남성형" +
                    "탈모(안드로겐성 탈모)입니다. 탈모 원격 처방 서비스는 남성형 탈모만을 대상으로 하고 있습니다.");
            map.put("treatAndRisk","피나스테리드는 남성에게 가장 흔하게 나타나는 탈모 유형을 치료할 수 있는 약으로서 FDA의 승인을" +
                    "받은 의약품입니다. 일반적으로는 안전하나 알레르기 반응, 유방암, 성 기능 장애, 그리고 전립선 암을" +
                    "유발할 수 있습니다." +
                    "피나스테리드 복용 시 발생하는 성기능 문제는 대부분 약 복용을 멈추면 회복됩니다. 하지만 소수의" +
                    "경우 이러한 증상이 약 복용을 멈추고도 40개월 간 지속되기도 합니다. 이러한 리스크는 환자의 나이가" +
                    "많을 수록 높아집니다." +
                    "특히 임신 중인 여성은 피나스테리드를 복용하거나 부숴진 조각을 만져선 안됩니다. 이는 태아에게" +
                    "심각한 손상을 입힐 수 있습니다. 더 자세한 사항은 피나스테리드 복약 안내문을 참고해 주세요.");
            map.put("comment","다음 항목 중 하나라도 해당된다면 탈모 원격 처방 서비스에 적합하지 않으며 서비스 제외 대상으로 구분 됩니다.");
            map.put("exceptService",arr);
            map.put("guideStart","지금부터 탈모약 처방을 위한 문진을 시작합니다. 본 문진은 총 13개의 질문을 포함하고 있습니다." +
                    "문진을 제출한 뒤에는 응답을 수정할 수 없으니 신중하게 임해 주시길 바랍니다." +
                    "문진을 완료했다고 하여 탈모약 원격 처방이 반드시 이루어지는 것은 아니며, 의료진이 환자의 상태가" +
                    "원격 처방에 적합하다고 판단할 경우에만 원격 처방이 이루어집니다. 약 처방전을 받지 못하셨을 경우" +
                    "진료비 3,000원을 전액 환불해 드립니다.");
        }
        return map;
    }

    @GetMapping("/review")
    @ResponseBody
    public Map<String, Object> review(){
        Map<String, Object> map = new HashMap<>();
        review []review = new review[3];

        review[0] = new review();
        review[1] = new review();
        review[2] = new review();

        review[0].setComment("비뇨기과 가기 꺼려졌는데, 이제 집에서 편하게 처방 받아요.");
        review[0].setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
        review[1].setComment("탈모 때문에 피부과 가기 꺼려졌는데, 이제 집에서 편하게 처방 받아요.");
        review[1].setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
        review[2].setComment("어플 좋아요.");
        review[2].setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());

        map.put("review",review);
        return map;
    }

    public class review{
        private String comment;
        private String Date;

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setDate(String simpleDateFormat) {
            Date = simpleDateFormat;
        }

        public String getComment() {
            return comment;
        }

        public String getDate() {
            return Date;
        }
    }
}
