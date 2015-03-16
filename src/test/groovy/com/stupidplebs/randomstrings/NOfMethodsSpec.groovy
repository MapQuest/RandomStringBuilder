package com.stupidplebs.randomstrings

import spock.lang.Specification

class NOfMethodsSpec extends Specification {
	def "nCharactersOf should append exactly n characters from the supplied string"() {
		given:
		def n = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(5) >>> [2, 0, 1, 4] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
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
			n * nextInt(52) >>> [12, 13, 24, 25, 1, 2, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nLetters(n).
			build()
			
		then:
		actualString == 'mnyzbcZ'

	}
	
	def "numbers should append exactly n numbers"() {
		given:
		def n = 3
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(10) >>> [2, 4, 7] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
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
			n * nextInt(26) >>> [12, 13, 24, 25, 1, 2] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
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
			n * nextInt(26) >>> [12, 13, 24, 25, 1, 2, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
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
			n * nextInt(2) >>> [0, 1, 1, 0, 1, 0] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nWhitespaceCharacters(n).
			build()
			
		then:
		actualString == ' \t\t \t '
		
	}
	
}
