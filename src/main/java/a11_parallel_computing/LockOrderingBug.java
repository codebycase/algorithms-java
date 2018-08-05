package a11_parallel_computing;

public class LockOrderingBug {
	public static class Account {
		private int balance;
		private int id;
		private static int globalId;

		Account(int balance) {
			this.balance = balance;
			this.id = ++globalId;
		}

		private boolean move(Account to, int amount) {
			synchronized (this) {
				synchronized (to) {
					if (amount > balance) {
						return false;
					}
					to.balance += amount;
					this.balance -= amount;
					System.out.println("returning true");
					return true;
				}
			}
		}

		// The id can be used as a global ordering on locks. 
		// Does not matter if lock1 equals lock2: since
		// Java locks are reentrant, we willre-acquire lock2.
		public boolean move2(Account to, int amount) {
			Account lock1 = (id < to.id) ? this : to;
			Account lock2 = (id < to.id) ? to : this;
			synchronized (lock1) {
				synchronized (lock2) {
					if (amount > balance) {
						return false;
					}
					to.balance += amount;
					this.balance -= amount;
					System.out.println("returning true");
					return true;
				}
			}
		}

		public static void transfer(final Account from, final Account to, final int amount) {
			Thread transfer = new Thread(new Runnable() {
				public void run() {
					from.move(to, amount);
				}
			});
			transfer.start();
		}
	}

	public static void main(String[] args) throws Exception {
		Account from = new Account(200);
		Account to = new Account(100);
		System.out.println("Initial balances = " + from.balance + " " + to.balance);
		Account.transfer(from, to, 50);
		Thread.sleep(100);
		System.out.println("New balances = " + from.balance + " " + to.balance);
	}

}
