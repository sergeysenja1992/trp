package ua.pp.ssenko.stories.domain

import javax.persistence.*

@Entity
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    @Column(unique = true)
    var email: String = ""

    constructor(email: String) {
        this.email = email;
    }

}

@Entity
class BoardShare(
        @OneToOne
        val account: Account,
        @OneToOne
        val board: Board,
        @Enumerated(EnumType.STRING)
        val shareType: ShareType,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
)

enum class ShareType {
    READ, EDIT
}

@Entity
class Board {
    constructor(name: String, owner: Account, description: String = "") {
        this.name = name
        this.owner = owner
        this.description = description
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    var name: String
    var description: String = ""

    @ManyToOne
    var owner: Account
}
