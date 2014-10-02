package hubbub

import com.grailsinaction.Profile
import com.grailsinaction.User
import spock.lang.Specification

/**
 *
 */
class QueryIntegrationSpec extends Specification {

    def "basic dynamic finders"() {
        given:
        new User(userId: 'glen', password: 'secret', profile: new Profile(email: 'glen@glensmith.com')).save()
        new User(userId: 'peter', password: 'sesame', profile: new Profile(homepage: 'http://www.peter.com')).save()

        when:
        def user = User.findByPassword('sesame')

        then:
        user.userId == 'peter'

        when:
        def now = new Date()
        def users = User.findAllByDateCreatedBetween(now - 1, now)

        then:
        users.size() == 2

        when:
        def profiles = Profile.findAllByEmailIsNotNull()

        then:
        profiles.size() == 1
    }

    def "query by example"() {
        given:
        new User(userId: 'glen', password: 'password').save()
        new User(userId: 'peter', password: 'password').save()
        new User(userId: 'cynthia', password: 'sesame').save()

        when:
        def userToFind = new User(userId: 'glen')
        def u1 = User.find(userToFind)

        then:
        u1.password == 'password'

        when:
        userToFind = new User(userId: 'cynthia')
        def u2 = User.find(userToFind)

        then:
        u2.userId == 'cynthia'

        when:
        userToFind = new User(password: 'password')
        def u3 = User.findAll(userToFind)

        then:
        u3*.userId == ['glen', 'peter']
    }
}
