package main.java.application;

public class Tuple4<A, B, C, D> {
    private A first;
    private B second;
    private C third;
    private D fourth;
    
	public Tuple4(A first, B second, C third, D fourth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
	}

	public final A getFirst() {
		return first;
	}

	public final void setFirst(A first) {
		this.first = first;
	}

	public final B getSecond() {
		return second;
	}

	public final void setSecond(B second) {
		this.second = second;
	}

	public final C getThird() {
		return third;
	}

	public final void setThird(C third) {
		this.third = third;
	}

	public final D getFourth() {
		return fourth;
	}

	public final void setFourth(D fourth) {
		this.fourth = fourth;
	}
	
    
}
