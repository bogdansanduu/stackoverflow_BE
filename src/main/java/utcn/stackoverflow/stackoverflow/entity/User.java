package utcn.stackoverflow.stackoverflow.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "f_name")
    private String firstName;

    @Column(name = "e_mail", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Content> contentList;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Vote> userContentList = new ArrayList<>();

    @Column(name = "score")
    private Double score = 0.0D;

    @Column(name = "banned")
    private boolean banned = false;

    @Column(name = "phone_number")
    private String phoneNumber;

    public User() {

    }

    public User(Long userId, String lastName, String firstName, String email, String password, String role) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @JsonManagedReference
    public List<Content> getContentList() {
        return contentList;
    }

    public void addContent(Content content) {
        this.contentList.add(content);
        content.setUser(this);
    }

    public void removeContent(Content content) {
        this.contentList.remove(content);
        content.setUser(null);
    }

    public void addVoteQuestion(Content content, int value) {
        Vote userContent = new Vote(this, content);
        userContent.setType("Question");
        userContent.setValue(value);
        userContentList.add(userContent);
        content.getUserContentList().add(userContent);
    }

    public void addVoteAnswer(Content content, int value) {
        Vote userContent = new Vote(this, content);
        userContent.setType("Answer");
        userContent.setValue(value);
        userContentList.add(userContent);
        content.getUserContentList().add(userContent);
    }

}
