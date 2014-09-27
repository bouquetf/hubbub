package com.grailsinaction


import spock.lang.*

/**
 *
 */
class PostIntegrationSpec extends Specification {
    def "First post"() {
        when:
        def user = new User(userId: "joe", password: "secret").save()
        def post1 = new Post(content: "First post... W00t!")
        user.addToPosts(post1)
        def post2 = new Post(content: "Second post...")
        user.addToPosts(post2)
        def post3 = new Post(content: "Third post...")
        user.addToPosts(post3)

        then:
        User.get(user.id).posts.size()
    }

    def "Accessing posts"() {
        when:
        def user = new User(userId: "joe", password: "secret").save()
        user.addToPosts(new Post(content: "First"))
        user.addToPosts(new Post(content: "Second"))
        user.addToPosts(new Post(content: "Third"))

        then:
        def foundUser = User.get(user.id)
        def postNames = foundUser.posts.collect { it.content }
        postNames.sort() == ['First', 'Second', 'Third']
    }

    def "Post with tags"() {
        when:
        def user = new User(userId: 'joe', password: 'secret').save()

        def tagGroovy = new Tag(name: 'groovy')
        def tagGrails = new Tag(name: 'grails')
        user.addToTags(tagGroovy)
        user.addToTags(tagGrails)

        then:
        def tagNames = user.tags*.name
        tagNames.sort() == ['grails', 'groovy']

        when:
        def groovyPost = new Post(content: 'A groovy post')
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)

        then:
        groovyPost.tags.size() == 1

        when:
        def bothPost = new Post(content: 'A groovy and grails post')
        user.addToPosts(bothPost)
        bothPost.addToTags(tagGroovy)
        bothPost.addToTags(tagGrails)

        then:
        bothPost.tags.size() == 2
    }
}
