package com.stupidplebs.randomstrings

import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges


@ConfineMetaClassChanges(List)
class AtLeastOneOfMethodsSpec extends Specification {
    def setupSpec() {
        List.metaClass.collectWithIndex = { body->
            [delegate, 0..<delegate.size()].transpose().collect(body)
        }
    }

	def "atLeastOneCharacterOf should append a single character when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many letters to append, up to randomUpperLimit
			1 * nextInt(6) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneCharacterOf('stupid').
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << "stupid".toList().collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "atLeastOneCharacterOf should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(6) >>> [1, 3, 2, 4]
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneCharacterOf('stupid').
			build()
			
		then:
		actualString == 'tpui'

	}

	def "atLeastOneCharacterOf should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
			5 * nextInt(6) >>> [1, 3, 2, 4, 0]
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneCharacterOf('stupid', butNoMoreThan).
			build()
			
		then:
		actualString == 'tpuis'

	}

	def "atLeastOneLetter should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
			1 * nextInt(52) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneLetter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z')).collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "atLeastOneLetter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(52) >>> [0, 25, 26, 51]
		}

		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneLetter().
			build()
			
		then:
		actualString == 'azAZ'

	}

	def "atLeastOneLetter should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
			5 * nextInt(52) >>> [0, 25, 26, 51, 1] // the letters to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneLetter(butNoMoreThan).
			build()
			
		then:
		actualString == 'azAZb'

	}

    def "atLeastOneAlphaNumeric should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many alphanumerics to append, 0 in this case
            1 * nextInt(62) >> charIdx
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            atLeastOneAlphaNumeric().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z') + ('0'..'9')).collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "atLeastOneAlphaNumeric should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 6 // how many alphanumerics to append, up to randomUpperLimit
            6 * nextInt(62) >>> [0, 25, 26, 51, 52, 61]
        }

        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            atLeastOneAlphaNumeric().
            build()
            
        then:
        actualString == 'azAZ09'

    }

    def "atLeastOneAlphaNumeric should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 7 // how many alphanumerics to append
            7 * nextInt(62) >>> [0, 25, 26, 51, 1, 52, 61] // the alphanumerics to append
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            atLeastOneAlphaNumeric(butNoMoreThan).
            build()
            
        then:
        actualString == 'azAZb09'

    }

	def "atLeastOneLowercaseLetter should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
			1 * nextInt(26) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneLowercaseLetter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << ('a'..'z').collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "atLeastOneLowercaseLetter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(26) >>> [0, 12, 13, 25]
		}

		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneLowercaseLetter().
			build()
			
		then:
		actualString == 'amnz'

	}

	def "atLeastOneLowercaseLetter should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(26) >>> [0, 12, 25] // the letters to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneLowercaseLetter(butNoMoreThan).
			build()
			
		then:
		actualString == 'amz'

	}

	def "atLeastOneUppercaseLetter should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
			1 * nextInt(26) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneUppercaseLetter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << ('A'..'Z').collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "atLeastOneUppercaseLetter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(26) >>> [0, 12, 13, 25]
		}

		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneUppercaseLetter().
			build()
			
		then:
		actualString == 'AMNZ'

	}

	def "atLeastOneUppercaseLetter should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(26) >>> [0, 12, 25] // the letters to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneUppercaseLetter(butNoMoreThan).
			build()
			
		then:
		actualString == 'AMZ'

	}

	def "atLeastOneNumber should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
			1 * nextInt(10) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneNumber().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << ('0'..'9').collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "atLeastOneNumber should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(10) >>> [0, 9, 6, 3]
		}

		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneNumber().
			build()
			
		then:
		actualString == '0963'

	}

	def "atLeastOneNumber should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(10) >>> [0, 2, 4] // the letters to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneNumber(butNoMoreThan).
			build()
			
		then:
		actualString == '024'

	}

	def "atLeastOneWhitespaceCharacter should append nothing when randomnessProvider.nextInt() returns 0"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 0 // how many append to return, 0 in this case
			1 * nextInt(2) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneWhitespaceCharacter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << [' ', '\t'].collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "atLeastOneWhitespaceCharacter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(2) >>> [1, 0, 0, 1]
		}

		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneWhitespaceCharacter().
			build()

		then:
		actualString == '\t  \t'

	}

	def "atLeastOneWhitespaceCharacter should append up to butNoMoreThan when specified"() {
		given:
		def butNoMoreThan = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
			3 * nextInt(2) >>> [0, 0, 1] // the letters to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneWhitespaceCharacter(butNoMoreThan).
			build()
			
		then:
		actualString == '  \t'

	}

}
