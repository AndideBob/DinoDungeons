package dinodungeons.gfx.text;

public enum Letter {
	SPACE(" ",0),
	UNKNOWN("#",0),
	A("A",1),
	B("B",2),
	C("C",3),
	D("D",4),
	E("E",5),
	F("F",6),
	G("G",7),
	H("H",8),
	I("I",9),
	J("J",10),
	K("K",11),
	L("L",12),
	M("M",13),
	N("N",14),
	O("O",15),
	P("P",16),
	Q("Q",17),
	R("R",18),
	S("S",19),
	T("T",20),
	U("U",21),
	V("V",22),
	W("W",23),
	X("X",24),
	Y("Y",25),
	Z("Z",26),
	COLON(".",27),
	QUESTIONMARK("?",28),
	EXCLAMATIONMARK("!",29),
	MINUS("-",30),
	APOSTROPHY("'",31),
	COMMA(",",32),
	DOUBLECOLON(":",33),
	NUMBER_0("0",34),
	NUMBER_1("1",35),
	NUMBER_2("2",36),
	NUMBER_3("3",37),
	NUMBER_4("4",38),
	NUMBER_5("5",39),
	NUMBER_6("6",40),
	NUMBER_7("7",41),
	NUMBER_8("8",42),
	NUMBER_9("9",43),
	GREATER_THAN(">",44),
	LESS_THAN("<",45),
	SLASH("/",46),
	PLUS("+",47),
	BRAKET_OPEN("[",48),
	BRAKET_CLOSE("]",49),
	ARROW_DOWN("*",50);
	
	private String character;
	private int idOnTileMap;

	private Letter(String character, int idOnTileMap){
		this.character = character;
		this.idOnTileMap = idOnTileMap;
	}
	
	public String getCharacter() {
		return character;
	}

	public int getIdOnTileMap() {
		return idOnTileMap;
	}
	
	public static Letter fromCharacter(char character){
		return fromCharacter("" + character);
	}
	
	public static Letter fromCharacter(String character){
		if(character == null || character.length() != 1){
			return UNKNOWN;
		}
		for(Letter letter : values()){
			if(character.toUpperCase().equals(letter.getCharacter())){
				return letter;
			}
		}
		return UNKNOWN;
	}
}
