package com.stupidplebs.randomstrings

import spock.lang.Specification

class ZeroOrMoreOfMethodsSpec extends Specification {
	def "zeroOrMoreCharactersOf should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreCharactersOf('stupid').
			build()
			
		then:
		actualString == ''

	}
	
	def "zeroOrMoreCharactersOf should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(6) >>> [1, 3, 2, 4]
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreCharactersOf('stupid').
			build()
			
		then:
		actualString == 'tpui'

	}

	def "zeroOrMoreCharactersOf should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
			5 * nextInt(6) >>> [1, 3, 2, 4, 0]
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreCharactersOf('stupid', butNoMoreThan).
			build()
			
		then:
		actualString == 'tpuis'

	}

	def "zeroOrMoreLetters should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreLetters().
			build()
			
		then:
		actualString == ''

	}
	
	def "zeroOrMoreLetters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(52) >>> [0, 25, 26, 51]
		}

		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreLetters().
			build()
			
		then:
		actualString == 'azAZ'

	}

	def "zeroOrMoreLetters should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
			5 * nextInt(52) >>> [0, 25, 26, 51, 1] // the letters to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreLetters(butNoMoreThan).
			build()
			
		then:
		actualString == 'azAZb'

	}

	def "zeroOrMoreLowercaseLetters should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreLowercaseLetters().
			build()
			
		then:
		actualString == ''

	}
	
	def "zeroOrMoreLowercaseLetters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(26) >>> [0, 12, 13, 25]
		}

		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreLowercaseLetters().
			build()
			
		then:
		actualString == 'amnz'

	}

	def "zeroOrMoreLowercaseLetters should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(26) >>> [0, 12, 25] // the letters to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreLowercaseLetters(butNoMoreThan).
			build()
			
		then:
		actualString == 'amz'

	}

	def "zeroOrMoreUppercaseLetters should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreUppercaseLetters().
			build()
			
		then:
		actualString == ''

	}
	
	def "zeroOrMoreUppercaseLetters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(26) >>> [0, 12, 13, 25]
		}

		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreUppercaseLetters().
			build()
			
		then:
		actualString == 'AMNZ'

	}

	def "zeroOrMoreUppercaseLetters should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(26) >>> [0, 12, 25] // the letters to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreUppercaseLetters(butNoMoreThan).
			build()
			
		then:
		actualString == 'AMZ'

	}

	def "zeroOrMoreNumbers should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreNumbers().
			build()
			
		then:
		actualString == ''

	}
	
	def "zeroOrMoreNumbers should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(10) >>> [0, 9, 6, 3]
		}

		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreNumbers().
			build()
			
		then:
		actualString == '0963'

	}

	def "zeroOrMoreNumbers should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(10) >>> [0, 2, 4] // the letters to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreNumbers(butNoMoreThan).
			build()
			
		then:
		actualString == '024'

	}

	def "zeroOrMoreWhitespaceCharacters should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreWhitespaceCharacters().
			build()
			
		then:
		actualString == ''

	}
	
	def "zeroOrMoreWhitespaceCharacters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(2) >>> [1, 0, 0, 1]
		}

		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreWhitespaceCharacters().
			build()

		then:
		actualString == '\t  \t'

	}

	def "zeroOrMoreWhitespaceCharacters should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(2) >>> [0, 0, 1] // the letters to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			zeroOrMoreWhitespaceCharacters(butNoMoreThan).
			build()
			
		then:
		actualString == '  \t'

	}


}
