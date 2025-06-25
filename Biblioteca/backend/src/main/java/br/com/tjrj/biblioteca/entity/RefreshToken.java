package br.com.tjrj.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import br.com.tjrj.biblioteca.entity.common.BaseEntity;


@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {

    @Column(nullable = false, unique = true, length = 128)
    private String token;

    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "revogado", nullable = false)
    private boolean revogado;
}
