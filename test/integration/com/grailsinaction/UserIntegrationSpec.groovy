package com.grailsinaction



import spock.lang.*

class UserIntegrationSpec extends Specification {

    def "First save ever"() {
        when:
            def user = new User(userId: "joe", password: "secret")

        then:
            user.save() != null
            user.id != null

        when:
            def foundUser = User.findById(user.id)

        then:
            foundUser.userId == "joe"
    }

    def "Save and Update"() {
        when:
        def user = new User(userId: "joe", password: "secret")

        then:
        user.save() != null

        when:
        def foundUser = User.findById(user.id)
        foundUser.password = "sesame"
        foundUser.save()

        then:
        foundUser.password == "sesame"
    }

    def "Save and delete"() {
        when:
        def user = new User(userId: "joe", password: "secret")

        then:
        user.save() != null

        when:
        def foundUser = User.findById(user.id)
        foundUser.delete()

        then:
        !User.exists(user.id)
    }

    def "Evil save"() {
        when:
        def user = new User(userId: "chuck_norris", password: "tiny")

        then:
        !user.validate()
        user.hasErrors()

        when:
        def errors = user.errors

        then:
        "size.toosmall" == errors.getFieldError("password").code
        "tiny" == errors.getFieldError("password").rejectedValue

        errors.getFieldError("userId") == null
    }

    def "Evil save corrected"() {
        when:
        def user = new User(userId: "chuck_norris", password: "tiny")

        then:
        !user.validate()
        user.hasErrors()
        user.save() == null

        when:
        user.password = "fistfist"

        then:
        user.validate()
        !user.hasErrors()
        user.save() != null
    }

    def "following"() {
        when:
        def glen = new User(userId: 'glen', password: 'password').save()
        def peter = new User(userId: 'peter', password: 'password').save()
        def sven = new User(userId: 'sven', password: 'password').save()

        glen.addToFollowing(peter)
        glen.addToFollowing(sven)

        then:
        glen.following.size() == 2

        when:
        sven.addToFollowing(peter)

        then:
        sven.following.size() == 1
    }
}
