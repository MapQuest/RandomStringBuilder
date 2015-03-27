package com.stupidplebs.randomstrings

import spock.lang.Shared
import spock.lang.Specification

import com.stupidplebs.randomstrings.provider.RandomnessProvider

class NOfMethodsSpec extends Specification {
    @Shared def random = new Random()
    
	def "nCharactersOf should append exactly n characters from the supplied string"() {
		given:
		def n = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(5) >>> [2, 0, 1, 4] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			nCharactersOf('!@#$%', n).
			build()
			
		then:
		actualString == '#!@%'

	}
	
	def "nLetters should append exactly n letters"() {
		given:
		def n = 7
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(RandomStringBuilder.LETTERS.length()) >>> [12, 13, 24, 25, 1, 2, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			nLetters(n).
			build()
			
		then:
		actualString == 'mnyzbcZ'

	}
	
    def "nAlphanumerics should append exactly n letters"() {
        given:
        def n = 7
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            n * nextInt(RandomStringBuilder.ALPHANUMERICS.length()) >>> [12, 13, 54, 25, 1, 60, 51] // the character indexes to append
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            nAlphanumerics(n).
            build()
            
        then:
        actualString == 'mn2zb8Z'

    }
    
	def "numbers should append exactly n numbers"() {
		given:
		def n = 3
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(RandomStringBuilder.NUMBERS.length()) >>> [2, 4, 7] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			nNumbers(n).
			build()
			
		then:
		actualString == '247'

	}
	
	def "nLowercaseLetters should append exactly n uppercase letters"() {
		given:
		def n = 6
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(RandomStringBuilder.LOWERCASE_LETTERS.length()) >>> [12, 13, 24, 25, 1, 2] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			nLowercaseLetters(n).
			build()
			
		then:
		actualString == 'mnyzbc'


	}
	
	def "nUppercaseLetters should append exactly n lowercase letters"() {
		given:
		def n = 6
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(RandomStringBuilder.UPPERCASE_LETTERS.length()) >>> [12, 13, 24, 25, 1, 2, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			nUppercaseLetters(n).
			build()
			
		then:
		actualString == 'MNYZBC'


	}
	
	def "nWhitespaceCharacters should append exactly n whitespace characters"() {
		given:
		def n = 6
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(RandomStringBuilder.WHITESPACE.length()) >>> [0, 1, 1, 0, 1, 0] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			nWhitespaceCharacters(n).
			build()
			
		then:
		actualString == ' \t\t \t '
		
	}
	
    def "nSpaces should append exactly n spaces characters"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider)
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            nSpaces(n).
            build()
            
        then:
        actualString == ' '.multiply(n)
        
        where: "run the test 50 times with random n"
        n << (1..50).collect { Math.abs(random.nextInt(200)) }
        
    }
    
}
