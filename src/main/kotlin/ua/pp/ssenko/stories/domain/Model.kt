package ua.pp.ssenko.stories.domain

import javax.persistence.*

@Entity
data class Account (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true)
    var email: String
)

@Entity
data class Group (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String,
    @ManyToOne
    var owner: Account
)

@Entity
data class Student (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val name: String
)


