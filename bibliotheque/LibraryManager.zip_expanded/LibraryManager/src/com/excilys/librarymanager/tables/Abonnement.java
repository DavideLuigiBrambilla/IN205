package com.excilys.librarymanager.tables;

	public enum Abonnement{
		BASIC(2),
		PREMIUM(5),
		VIP(20);

		private final int num;
 
        Abonnement(int num) {
            this.num = num;
        }
        
        public int getNum() {
        	return this.num;
        }
    }
