package hanium.highwayspring.school;
import hanium.highwayspring.dept.Dept;
import hanium.highwayspring.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "school_tb")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="street_address")
    private String streetAddress; // 학교도로명 주소 (SCHUL_RDNMA)

    @Column(name="establishment_type")
    private String establishmentType; // 설립구분 (FOND_SC_CODE)

    @Column(name="office_phone")
    private String officePhone; // 교무실 전화번호 (USER_TELNO_SW)

    @Column(name="zip_code")
    private Long zipCode; // 우편번호 (ZIP_CODE)

    @Column(name="detailed_address")
    private String detailedAddress; // 학교도로명 우편번호 (SCHUL_RDNZC)

    @Column(name="phone_number")
    private String phoneNumber; // 전화번호 (USER_TELNO)

    @Column(name="gender_type")
    private String genderType; // 남녀공학 구분 (COEDU_SC_CODE)

    @Column(name="fax_number")
    private String faxNumber; // 팩스번호 (PERC_FAXNO)

    @Column(name="website")
    private String website; // 홈페이지 주소 (HMPG_ADRES)

    @Column(name="address_details")
    private String addressDetails; // 상세주소 내역 (DTLAD_BRKDN)

    @Column(name="address")
    private String address; // 주소내역 (ADRES_BRKDN)

    @Column(name="location_code")
    private Long locationCode; // 지역코드 (ADRCD_CD)

    @Column(name="school_address_details")
    private String schoolAddressDetails; // 학교도로명 상세주소 (SCHUL_RDNDA)

    @Column(name="school_name")
    private String schoolName; // 학교명 (SCHUL_NM)

    @Column(name="admin_phone")
    private String adminPhone; //행정실 전화번호 (USER_TELNO_GA)

    @Column(name="school_image")
    private String schoolImage; //학교 로고 이미지

    //review에서 schoolId값 받아와서 저장할 때 필요한 생성자입니다.
    public School(Long schoolId) {
        this.id = schoolId;
    }
}