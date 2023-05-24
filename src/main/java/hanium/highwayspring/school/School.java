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
    private String SCHUL_RDNMA;
    private String SCHUL_FOND_TYP_CODE;
    private Long FOAS_MEMRD;
    private String DGHT_SC_CODE;
    private String FOND_SC_CODE;
    private String USER_TELNO_SW;
    private Long LTTUD;
    private Long ZIP_CODE;
    private Long SCHUL_KND_SC_CODE;
    private Long LGTUD;
    private Long SCHUL_RDNZC;
    private String DTLAD_BRKDN;
    private String USER_TELNO;
    private String ADRCD_NM;
    private String COEDU_SC_CODE;
    private String PERC_FAXNO;
    private String JU_ORG_NM;
    private String ATPT_OFCDC_ORG_NM;
    private String JU_ORG_CODE;
    private String HMPG_ADRES;
    private String ADRES_BRKDN;
    private String SCHUL_CODE;
    private Long FOND_YMD;
    private String ATPT_OFCDC_ORG_CODE;
    private Long LCTN_SC_CODE;
    private Long ADRCD_CD;
    private Long ADRCD_ID;
    private String HS_KND_SC_NM;
    private String SCHUL_RDNDA;
    private String BNHH_YN;
    private String SCHUL_NM;
    private String USER_TELNO_GA;
}
