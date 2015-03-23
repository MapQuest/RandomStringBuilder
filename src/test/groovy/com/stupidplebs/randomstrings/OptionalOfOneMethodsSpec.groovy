package com.stupidplebs.randomstrings

import com.stupidplebs.randomstrings.provider.RandomnessProvider;

import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges


@ConfineMetaClassChanges(List)
class OptionalOfOneMethodsSpec extends Specification {
    def setupSpec() {
        List.metaClass.collectWithIndex = { body->
            [delegate, 0..<delegate.size()].transpose().collect(body)
        }
    }

	def "optionalCharacterOf should append 0 characters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
			0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			is("Value").
			optionalCharacterOf('!@#$%').
			build()
			
		then:
		actualString == 'Value'

	}
		  
	def "optionalLetter should append 0 letters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
			0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			is("Value").
			optionalLetter().
			build()
			
		then:
		actualString == 'Value'

	}
	
    def "optionalAlphaNumeric should append 0 letters when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            is("Value").
            optionalAlphaNumeric().
            build()
            
        then:
        actualString == 'Value'

    }
    
	def "optionalNumber should append 0 numbers when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
			0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			is("Value").
			optionalNumber().
			build()
			
		then:
		actualString == 'Value'

	}
	
	def "optionalLowercaseLetter should append 0 lowercase letters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
			0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			is("Value").
			optionalLowercaseLetter().
			build()
			
		then:
		actualString == 'Value'

	}
	
	def "optionalUppercaseLetter should append 0 uppercase letters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
			0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			is("Value").
			optionalUppercaseLetter().
			build()
			
		then:
		actualString == 'Value'

	}
	
	def "optionalWhitespaceCharacter should append 0 whitespace characters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
			0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			is("Value").
			optionalWhitespaceCharacter().
			build()
			
		then:
		actualString == 'Value'

	}

	def "optionalSpace should append 0 spaces when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
			0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			is("Value").
			optionalSpace().
			build()
			
		then:
		actualString == 'Value'

	}

	def "optionalCharacterOf should append 1 character when nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(5) >> 3 // the character index to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			optionalCharacterOf('!@#$%').
			build()
			
		then:
		actualString == '$'

	}
		  
	def "optionalLetter should append up 1 letter when nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(52) >> 12 // the character index to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			optionalLetter().
			build()
			
		then:
		actualString == 'm'

	}
	
    def "optionalAlphaNumeric should append up 1 alphanumeric when nextBoolean returns true"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> true
            1 * nextInt(62) >> charIdx // the character index to append
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            optionalAlphaNumeric().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z') + ('0'..'9')).collectWithIndex { it, idx -> [idx, it] }

    }
    
	def "optionalNumber should append 1 number nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(10) >> 8 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			optionalNumber().
			build()
			
		then:
		actualString == '8'

	}
	
	def "optionalLowercaseLetter should append 1 lowercase letter nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(26) >> 12 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			optionalLowercaseLetter().
			build()
			
		then:
		actualString == 'm'

	}
	
	def "optionalUppercaseLetter should append 1 uppercase letter nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(26) >> 12 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			optionalUppercaseLetter().
			build()
			
		then:
		actualString == 'M'

	}
	
	def "optionalWhitespaceCharacter should append 1 whitespace character nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(2) >> 0 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			optionalWhitespaceCharacter().
			build()
			
		then:
		actualString == ' '

	}

	def "optionalSpace should append 1 space nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			optionalSpace().
			build()
			
		then:
		actualString == ' '

	}

}
