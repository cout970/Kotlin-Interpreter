package com . cout970 . ps
import java . io . File
import java . util . regex . Pattern
fun main ( args : Array < String > ) {
	val stream = FileCharInputStream ( File ( "./src/main/kotlin/test.ls" ) )
	val tokenizer = Tokenizer ( stream )
	var indent = 0
	var token : Token
	var look : Token = tokenizer . readToken ( )
	do {
		token = look
		look = tokenizer . readToken ( )
		if ( token . type == TokenType . LINE_FEED ) {
			println ( )
			if ( look . type == TokenType . CURLY_BRACKET_CLOSE ) {
				repeat ( indent - 1 ) {
					print ( "\t" )
				}
			} else {
				repeat ( indent ) {
					print ( "\t" )
				}
			}
		} else if ( token . type == TokenType . STRING ) {
			print ( "\"${token.text}\" " )
		} else if ( token . type == TokenType . CHAR ) {
			val code = "\\u" + "%4x" . format ( token . text [ 0 ] . toShort ( ) . toInt ( ) ) . replace ( " " , "0" )
			print ( "\'$code\' " )
		} else if ( token . type == TokenType . CURLY_BRACKET_OPEN ) {
			indent ++
			print ( token . text + " " )
		} else if ( token . type == TokenType . CURLY_BRACKET_CLOSE ) {
			indent --
			print ( "} " )
		} else {
			print ( token . text + " " )
		}
	} while ( token . type != TokenType . UNKNOWN )
	println ( )
	System . out . flush ( )
	Thread . sleep ( 500 )
}
interface CharInputSteam {
	fun read ( ) : Char
	fun getFile ( ) : String
	fun getLine ( ) : Int
	fun getColumn ( ) : Int
}
class FileCharInputStream ( val file : File ) : CharInputSteam {
	private var line = - 1
	private var column = 0
	private val cache : List < String >
	init {
		cache = file . readLines ( ) . map { it + '\u000a' }
		if ( cache . size == 0 ) column = - 1
	}
	override fun read ( ) : Char {
		if ( column == - 1 ) {
			return ( - 1 ) . toChar ( )
		}
		line ++
		while ( column != - 1 && cache [ column ] . length <= line ) {
			line = 0
			column ++
			if ( column >= cache . size ) {
				column = - 1
			}
		}
		if ( column == - 1 ) {
			return ( - 1 ) . toChar ( )
		}
		val tmp = cache [ column ] [ line ]
		return tmp
	}
	override fun getFile ( ) : String = file . name
	override fun getLine ( ) : Int = line
	override fun getColumn ( ) : Int = column
}
enum class TokenType ( ) {
	IDENTIFIER ,
	NUMBER ,
	TRUE ,
	FALSE ,
	STRING ,
	CHAR ,
	VAR ,
	VAL ,
	CLASS ,
	ATTRIBUTE_PUBLIC ,
	ATTRIBUTE_PRIVATE ,
	ATTRIBUTE_PROTECTED ,
	ATTRIBUTE_INTERNAL ,
	ENUM ,
	FUN ,
	IF ,
	ELSE ,
	WHILE ,
	FOR ,
	WHEN ,
	WHERE ,
	INLINE ,
	NOINLINE ,
	PACKAGE ,
	IMPORT ,

	DOT ,
	COMMA ,
	COLON ,
	PAREN_OPEN ,
	PAREN_CLOSE ,
	BRACKET_OPEN ,
	BRACKET_CLOSE ,
	CURLY_BRACKET_OPEN ,
	CURLY_BRACKET_CLOSE ,
	ASSIGN ,
	EQUALS ,
	NOT_EQUALS ,
	IDENTITY_EQUALS ,
	IDENTITY_NOT_EQUALS ,
	MORE ,
	LESS ,
	MORE_EQUAL ,
	LESS_EQUAL ,
	PLUS ,
	ATOMIC_INCREMENT ,
	ATOMIC_DECREMENT ,
	INCREMENT_ASSIGN ,
	DECREMENT_ASSIGN ,
	TIMES_ASSIGN ,
	DIV_ASSIGN ,
	MOD_ASSIGN ,
	ARROW ,
	MINUS ,
	STAR ,
	DIV ,
	MOD ,
	AT ,
	OR ,
	AND ,
	LINE_FEED ,
	SEMICOLON ,
	QUESTION_MARK ,
	UNKNOWN ;
	companion object {
		val IDENTIFIER_TOKENS = mapOf < String , TokenType > (
		"true" to TRUE ,
		"false" to FALSE ,
		"var" to VAR ,
		"val" to VAL ,
		"class" to CLASS ,
		"public" to ATTRIBUTE_PUBLIC ,
		"private" to ATTRIBUTE_PRIVATE ,
		"protected" to ATTRIBUTE_PROTECTED ,
		"internal" to ATTRIBUTE_INTERNAL ,
		"enum" to ENUM ,
		"fun" to FUN ,
		"if" to IF ,
		"else" to ELSE ,
		"while" to WHILE ,
		"for" to FOR ,
		"when" to WHEN ,
		"where" to WHERE ,
		"inline" to INLINE ,
		"noinline" to NOINLINE
		)
		val SPECIAL_TOKENS = mapOf < String , TokenType > (
		"." to DOT ,
		"," to COMMA ,
		":" to COLON ,
		";" to SEMICOLON ,
		"(" to PAREN_OPEN ,
		")" to PAREN_CLOSE ,
		"[" to BRACKET_OPEN ,
		"]" to BRACKET_CLOSE ,
		"{" to CURLY_BRACKET_OPEN ,
		"}" to CURLY_BRACKET_CLOSE ,
		">" to MORE ,
		"<" to LESS ,
		"+" to PLUS ,
		"-" to MINUS ,
		"*" to STAR ,
		"/" to DIV ,
		"%" to MOD ,
		"@" to AT ,
		"?" to QUESTION_MARK
		)
	}
}
data class Token ( val text : String , val type : TokenType , val file : String , val line : Int , val column : Int )
class Tokenizer ( val stream : CharInputSteam ) {
	companion object {
		val PATTERN_NUMBER = Pattern . compile ( "[0-9]+" )
		val PATTERN_IDENTIFIER_FIRST = Pattern . compile ( "[a-zA-Z]" )
		val PATTERN_IDENTIFIER = Pattern . compile ( "[a-zA-Z0-9_]" )
	}
	var look = stream . read ( )
	var line = 0
	var column = 0
	var file = ""

	private fun read ( ) {
		look = stream . read ( )
	}
	private fun reset ( ) {
		line = stream . getLine ( )
		column = stream . getColumn ( )
		file = stream . getFile ( )
	}
	fun readToken ( ) : Token {
		return tryReadToken ( )
	}
	private fun tryReadToken ( ) : Token {
		while ( look == '\u0020' || look == '\u0009' ) {
			read ( )
		}
		reset ( )
		if ( look == '\u000a' ) {
			read ( )
			while ( look == '\u000a' ) {
				read ( )
			}
			return Token ( "\n" , TokenType . LINE_FEED , file , line , column )
		} else if ( PATTERN_NUMBER . matcher ( look . toString ( ) ) . matches ( ) ) {
			var acum = ""
			do {
				acum += look
				read ( )
			} while ( PATTERN_NUMBER . matcher ( look . toString ( ) ) . matches ( ) )
			return Token ( acum , TokenType . NUMBER , file , line , column )
		} else if ( PATTERN_IDENTIFIER_FIRST . matcher ( look . toString ( ) ) . matches ( ) ) {
			var acum = ""
			do {
				acum += look
				read ( )
			} while ( PATTERN_IDENTIFIER . matcher ( look . toString ( ) ) . matches ( ) )
			for ( ( key , value ) in TokenType . IDENTIFIER_TOKENS ) {
				if ( key == acum ) {
					return Token ( acum , value , file , line , column )
				}
			}
			return Token ( acum , TokenType . IDENTIFIER , file , line , column )
		} else if ( look == '\u0022' ) {
			var acum = ""
			read ( )
			var last = '\u0020'
			while ( look != '\u0022' || last == '\u005c' ) {
				acum += look
				last = look
				read ( )
			}
			read ( )
			return Token ( acum , TokenType . STRING , file , line , column )
		} else if ( look == '\u002f' ) {
			read ( )
			if ( look == '\u002f' ) {
				read ( )
				while ( look != '\u000a' ) {
					read ( )
				}
				return tryReadToken ( )
			} else if ( look == '\u002a' ) {
				read ( )
				while ( true ) {
					if ( look == '\u002a' ) {
						read ( )
						if ( look == '\u002f' ) {
							read ( )
							break
						}
					} else {
						read ( )
					}
				}
				while ( look == '\u000a' || look == '\u0020' ) {
					read ( )
				}
				return tryReadToken ( )
			} else {
				return Token ( "/" , TokenType . DIV , file , line , column )
			}
		} else if ( look == '\u0027' ) {
			read ( )
			var temp = look . toString ( )
			if ( look == '\u005c' ) {
				read ( )
				temp = when ( look ) {
					'\u0074' -> {
						read ( ) ; '\u0009' . toString ( )
					}
					'\u006e' -> {
						read ( ) ; '\u000a' . toString ( )
					}
					'\u0072' -> {
						read ( ) ; '\u000d' . toString ( )
					}
					'\u0030' -> {
						read ( ) ; 0 . toString ( )
					}
					'\u0075' -> {
						var acum = "u"
						read ( )
						for ( i in 0 until 4 ) {
							acum += look
							read ( )
						}
						acum
					}
					else -> {
						val t = look . toString ( )
						read ( ) ; t
					}
				}
			} else {
				read ( )
			}
			if ( look == '\u0027' ) {
				read ( )
				return Token ( temp , TokenType . CHAR , file , line , column )
			}
			return Token ( temp + look , TokenType . UNKNOWN , file , line , column )
		} else {
			val temp = look . toString ( )
			read ( )
			var token : Token ? = null
			for ( ( key , value ) in TokenType . SPECIAL_TOKENS . filter { it . key . length == 1 } ) {
				if ( temp == key ) {
					token = Token ( temp . toString ( ) , value , file , line , column )
					break
				}
			}
			if ( temp == "!" && look == '\u003d' ) {
				read ( )
				if ( look == '\u003d' ) {
					read ( )
					return Token ( "!=" , TokenType . IDENTITY_NOT_EQUALS , file , line , column )
				}
				return Token ( "!=" , TokenType . NOT_EQUALS , file , line , column )
			}
			if ( temp == "=" ) {
				if ( look == '\u003d' ) {
					read ( )
					if ( look == '\u003d' ) {
						read ( )
						return Token ( "===" , TokenType . IDENTITY_EQUALS , file , line , column )
					}
					return Token ( "==" , TokenType . NOT_EQUALS , file , line , column )
				} else {
					return Token ( "=" , TokenType . ASSIGN , file , line , column )
				}
			}
			if ( temp == "+" && look == '\u002b' ) {
				read ( )
				return Token ( "++" , TokenType . ATOMIC_INCREMENT , file , line , column )
			}
			if ( temp == "-" && look == '\u002d' ) {
				read ( )
				return Token ( "--" , TokenType . ATOMIC_DECREMENT , file , line , column )
			}
			if ( temp == "+" && look == '\u003d' ) {
				read ( )
				return Token ( "+=" , TokenType . INCREMENT_ASSIGN , file , line , column )
			}
			if ( temp == "-" && look == '\u003d' ) {
				read ( )
				return Token ( "-=" , TokenType . DECREMENT_ASSIGN , file , line , column )
			}
			if ( temp == "*" && look == '\u003d' ) {
				read ( )
				return Token ( "*=" , TokenType . TIMES_ASSIGN , file , line , column )
			}
			if ( temp == "/" && look == '\u003d' ) {
				read ( )
				return Token ( "/=" , TokenType . DIV_ASSIGN , file , line , column )
			}
			if ( temp == "%" && look == '\u003d' ) {
				read ( )
				return Token ( "%=" , TokenType . MOD_ASSIGN , file , line , column )
			}
			if ( temp == ">" && look == '\u003d' ) {
				read ( )
				return Token ( ">=" , TokenType . MORE_EQUAL , file , line , column )
			}
			if ( temp == "-" && look == '\u003e' ) {
				read ( )
				return Token ( "->" , TokenType . ARROW , file , line , column )
			}
			if ( temp == "<" && look == '\u003d' ) {
				read ( )
				return Token ( "<=" , TokenType . MORE_EQUAL , file , line , column )
			}
			if ( temp == "|" && look == '\u007c' ) {
				read ( )
				return Token ( "||" , TokenType . OR , file , line , column )
			}
			if ( temp == "&" && look == '\u0026' ) {
				read ( )
				return Token ( "&&" , TokenType . AND , file , line , column )
			}
			if ( token != null ) return token
			return Token ( temp + look , TokenType . UNKNOWN , file , line , column )
		}
	}
}