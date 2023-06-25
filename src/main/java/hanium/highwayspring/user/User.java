package hanium.highwayspring.user;

import hanium.highwayspring.school.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "USER_TB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private String pwd;
    private String name;
    private String email;
    private String gender;
    private Long age;
    private Long role;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

}
