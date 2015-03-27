package com.stupidplebs.randomstrings

import com.stupidplebs.randomstrings.provider.RandomnessProvider;

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
        def chars = 'stupid'
        
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many letters to append, up to randomUpperLimit
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 4 // how many letters to append, up to randomUpperLimit
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many append to return, 0 in this case
			1 * nextInt(RandomStringBuilder.LETTERS.length()) >> charIdx
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(RandomStringBuilder.LETTERS.length()) >>> [0, 25, 26, 51]
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
			5 * nextInt(RandomStringBuilder.LETTERS.length()) >>> [0, 25, 26, 51, 1] // the letters to append
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
            1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many alphanumerics to append, 0 in this case
            1 * nextInt(RandomStringBuilder.ALPHANUMERICS.length()) >> charIdx
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
            1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 6 // how many alphanumerics to append, up to randomUpperLimit
            6 * nextInt(RandomStringBuilder.ALPHANUMERICS.length()) >>> [0, 25, 26, 51, 52, 61]
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
            7 * nextInt(RandomStringBuilder.ALPHANUMERICS.length()) >>> [0, 25, 26, 51, 1, 52, 61] // the alphanumerics to append
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many append to return, 0 in this case
			1 * nextInt(RandomStringBuilder.LOWERCASE_LETTERS.length()) >> charIdx
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(RandomStringBuilder.LOWERCASE_LETTERS.length()) >>> [0, 12, 13, 25]
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
			3 * nextInt(RandomStringBuilder.LOWERCASE_LETTERS.length()) >>> [0, 12, 25] // the letters to append
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many append to return, 0 in this case
			1 * nextInt(RandomStringBuilder.UPPERCASE_LETTERS.length()) >> charIdx
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(RandomStringBuilder.UPPERCASE_LETTERS.length()) >>> [0, 12, 13, 25]
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
			3 * nextInt(RandomStringBuilder.UPPERCASE_LETTERS.length()) >>> [0, 12, 25] // the letters to append
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many append to return, 0 in this case
			1 * nextInt(RandomStringBuilder.NUMBERS.length()) >> charIdx
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(RandomStringBuilder.NUMBERS.length()) >>> [0, 9, 6, 3]
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
			3 * nextInt(RandomStringBuilder.NUMBERS.length()) >>> [0, 2, 4] // the letters to append
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many append to return, 0 in this case
			1 * nextInt(RandomStringBuilder.WHITESPACE.length()) >> charIdx
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
			1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 4 // how many letters to append, up to randomUpperLimit
			4 * nextInt(RandomStringBuilder.WHITESPACE.length()) >>> [1, 0, 0, 1]
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
			3 * nextInt(RandomStringBuilder.WHITESPACE.length()) >>> [0, 0, 1] // the letters to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			atLeastOneWhitespaceCharacter(butNoMoreThan).
			build()
			
		then:
		actualString == '  \t'

	}

    def "atLeastOneSpace should append one space when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 0 // how many append to return, 0 in this case
            1 * nextInt(1) >> 0
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            atLeastOneSpace().
            build()
            
        then:
        actualString == ' '

    }
    
    def "atLeastOneSpace should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(RandomStringBuilder.RANDOM_UPPER_LIMIT) >> 4 // how many spaces to append
            4 * nextInt(1) >> 0
        }

        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            atLeastOneSpace().
            build()

        then:
        actualString == ' '.multiply(4)

    }

    def "atLeastOneSpace should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many spaces to append
            3 * nextInt(1) >> 0
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            atLeastOneSpace(butNoMoreThan).
            build()
            
        then:
        actualString == ' '.multiply(3)

    }

}
