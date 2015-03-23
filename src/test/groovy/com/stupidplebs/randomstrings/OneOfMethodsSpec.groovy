package com.stupidplebs.randomstrings

import com.stupidplebs.randomstrings.provider.RandomnessProvider;

import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges


@ConfineMetaClassChanges(List)
class OneOfMethodsSpec extends Specification {
    def setupSpec() {
        List.metaClass.collectWithIndex = { body->
            [delegate, 0..<delegate.size()].transpose().collect(body)
        }
    }

	def "oneElementOf should append random element from list supplied"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(4) >>> elementIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneElementOf(["one", "two", "three", "four"]).
			build()
			
		then:
		actualString == expectedOutput

		where:
		[elementIdx, expectedOutput] << ["one", "two", "three", "four"].collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "oneKeyOf should append random key from list supplied"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(3) >>> elementIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneKeyOf(["one": "foo", "two": "bar", "three": "baz"]).
			build()
			
		then:
		actualString == expectedOutput

		where:
		[elementIdx, expectedOutput] << ["one", "two", "three"].collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "oneValueOf should append random value from list supplied"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(3) >>> elementIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneValueOf(["one": "foo", "two": "bar", "three": "baz"]).
			build()
			
		then:
		actualString == expectedOutput

		where:
		[elementIdx, expectedOutput] << ["foo", "bar", "baz"].collectWithIndex { it, idx -> [idx, it] }
		
	}
	
	def "oneCharacterOf should return a single character from the input string"() {
		given:
		def characters = 'stupid'
		
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(characters.length()) >> idx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneCharacterOf(characters).
			build()
			
		then:
		actualString == expectedCharacter

		where:
		idx | expectedCharacter
		0   | 's'
		1   | 't'
		2   | 'u'
		3   | 'p'
		4   | 'i'
		5   | 'd'
		
	}
	
	def "oneLetter should return a single letter in the a-zA-Z range"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(52) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneLetter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z')).collectWithIndex { it, idx -> [idx, it] }

	}
	
    def "oneAlphaNumeric should return a single letter or number in the a-zA-Z0-9 range"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(62) >> charIdx
        }
        
        when:
        def actualString = new RandomStringBuilder(randomnessProvider).
            oneAlphaNumeric().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z') + ('0'..'9')).collectWithIndex { it, idx -> [idx, it] }

    }
    
	def "oneNumber should return a single number in the 0-9 range"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(10) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneNumber().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << ('0'..'9').collectWithIndex { it, idx -> [idx, it] }

	}
	
	def "oneLowercaseLetter should return a single letter in the a-z range"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(26) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneLowercaseLetter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << ('a'..'z').collectWithIndex { it, idx -> [idx, it] }

	}
	
	def "oneUppercaseLetter should return a single letter in the A-Z range"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(26) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneUppercaseLetter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << ('A'..'Z').collectWithIndex { it, idx -> [idx, it] }

	}
	
	def "oneWhitespaceCharacter should return ' ' when randomnessProvider returns 0 or '\t' when randomnessProvider returns 1"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * nextBoolean()
			1 * nextInt(2) >> charIdx
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
			oneWhitespaceCharacter().
			build()
			
		then:
		actualString == expectedOutput

		where:
		[charIdx, expectedOutput] << [' ', '\t'].collectWithIndex { it, idx -> [idx, it] }

	}
	
}
