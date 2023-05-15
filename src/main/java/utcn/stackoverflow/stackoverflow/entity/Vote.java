package utcn.stackoverflow.stackoverflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name="votes")
public class Vote {

    @EmbeddedId
    private UserContentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contentId")
    private Content content;

    @Column(name="type")
    private String type;

    @Column(name="value")
    private int value;

    public Vote(){}

    public Vote(User user, Content content){
        this.user = user;
        this.content = content;
        this.id = new UserContentId(user.getUserId(), content.getContentId());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote that = (Vote) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, content);
    }
}
