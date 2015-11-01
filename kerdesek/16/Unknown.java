

interface Unknown extends Other {
	//int n = 0;
	static Integer n = 0;
	
	static int f() {
		return n;
	}
	
	static Known create(Other o) {
		return new Known();
	}
	
	default void g() {}
}
