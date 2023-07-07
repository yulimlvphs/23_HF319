package hanium.highwayspring.school;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "SCHOOL_TB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String schoolStreetAddress; //학교도로명 주소 (SCHUL_RDNMA)
    private String establishmentType; //설립구분 (FOND_SC_CODE)
    private String officePhoneNumber; //교무실 전화번호 (USER_TELNO_SW)
    private Long zipCode; //우편번호 (ZIP_CODE)
    private String detailedAddress;  //학교도로명 우편번호 (SCHUL_RDNZC)
    private String telephoneNumber; //전화번호 (USER_TELNO)
    private String genderType; //남녀공학 구분 (COEDU_SC_CODE)
    private String faxNumber; //팩스번호 (PERC_FAXNO)
    private String websiteAddress; //홈페이지 주소 (HMPG_ADRES)
    private String addressDetailsInfo; // 상세주소 내역 (DTLAD_BRKDN)
    private String addressInfo; //주소내역 (ADRES_BRKDN)
    private Long locationCode; //지역코드 (ADRCD_CD)
    private String schoolAddressDetails; //학교도로명 상세주소 (SCHUL_RDNDA)
    private String schoolName; //학교명 (SCHUL_NM)
    private String administrationPhoneNumber; //행정실 전화번호 (USER_TELNO_GA)

    //review에서 schoolId값 받아와서 저장할 때 필요한 생성자입니다.
    public School(Long schoolId) {
        this.id = schoolId;
    }
}