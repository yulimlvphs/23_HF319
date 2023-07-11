package hanium.highwayspring.user;
import hanium.highwayspring.school.School;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "USER_TB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String pwd;
    private String uid;
    private String name;
    private String email;
    private String gender;
    private Long age;
    private Long role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schoolId")
    private School schoolId;
}
