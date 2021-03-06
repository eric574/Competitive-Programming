package codebook.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Primes {

  // Sieve of Eratosthenes in O(N log log N)
  static ArrayList<Integer> getPrimesEratosthenes (int N) {
    boolean[] prime = new boolean[N + 1];
    ArrayList<Integer> ret = new ArrayList<Integer>();

    Arrays.fill(prime, true);

    for (int i = 2; i * i <= N; i++)
      if (prime[i])
        for (int j = i * i; j <= N; j += i)
          prime[j] = false;

    for (int i = 2; i <= N; i++)
      if (prime[i])
        ret.add(i);

    return ret;
  }

  static ArrayList<Integer> getPrimesLinear (int N) {
    int[] lp = new int[N + 1];
    ArrayList<Integer> ret = new ArrayList<Integer>();

    for (int i = 2; i <= N; i++) {
      if (lp[i] == 0) {
        lp[i] = i;
        ret.add(i);
      }
      for (int j = 0; j < ret.size(); j++) {
        if (ret.get(j) > lp[i] || i * ret.get(j) > N)
          break;
        lp[i * ret.get(j)] = ret.get(j);
      }
    }
    return ret;
  }

  static ArrayList<Integer> getPrimesAtkins (int N) {
    boolean[] prime = new boolean[N + 1];
    ArrayList<Integer> ret = new ArrayList<Integer>();

    prime[2] = true;
    prime[3] = true;

    int num;

    for (int i = 1; i * i <= N; i++) {
      for (int j = 1; j * j <= N; j++) {
        num = 4 * i * i + j * j;
        if (num <= N && (num % 12 == 1 || num % 12 == 5))
          prime[num] = true;

        num = 3 * i * i + j * j;
        if (num <= N && (num % 12 == 7))
          prime[num] = true;

        if (i > j) {
          num = (3 * i * i - j * j);
          if (num <= N && num % 12 == 11)
            prime[num] = true;
        }
      }
    }

    for (int i = 5; i * i <= N; i++)
      if (prime[i])
        for (int j = i * i; j <= N; j += i)
          prime[j] = false;

    for (int i = 2; i <= N; i++)
      if (prime[i])
        ret.add(i);

    return ret;
  }

  static ArrayList<Integer> genPrimes (int l, int h) {
    int sqrth = (int)Math.ceil(Math.sqrt(h));
    int sqrtsqrth = (int)Math.ceil(Math.sqrt(sqrth));

    boolean[] prime1 = new boolean[sqrth + 1];
    boolean[] prime2 = new boolean[h - l + 1];

    ArrayList<Integer> ret = new ArrayList<Integer>();

    Arrays.fill(prime1, true);
    Arrays.fill(prime2, true);

    for (int i = 2; i <= sqrtsqrth; i++) {
      if (prime1[i])
        for (int j = i * i; j <= sqrth; j += i)
          prime1[j] = false;
    }
    for (int i = 2, n = h - l; i <= sqrth; i++) {
      if (prime1[i])
        for (int j = l / i * i - l; j <= n; j += i)
          if (j >= 0 && j + l != i)
            prime2[j] = false;
    }
    for (int i = l > 1 ? l : 2; i <= h; i++)
      if (prime2[i - l])
        ret.add(i);
    return ret;
  }

  static boolean isPrime (int n) {
    if (n <= 1)
      return false;
    if (n == 2)
      return true;
    if (n % 2 == 0)
      return false;
    if (n < 9)
      return true;
    if (n % 3 == 0)
      return false;

    int counter = 5;
    while ((counter * counter) <= n) {
      if (n % counter == 0)
        return false;
      if (n % (counter + 2) == 0)
        return false;
      counter += 6;
    }
    return true;
  }

  static void primeFactors (int n) {
    while (n % 2 == 0) {
      System.out.println("2 ");
      n = n / 2;
    }
    for (int i = 3; i * n <= n; i += 2) {
      while (n % i == 0) {
        System.out.println(i + " ");
        n = n / i;
      }
    }
    if (n > 2)
      System.out.println(n + " ");
  }

  static int countDivisors (int n) {
    int x = 2;
    int numOfFactors = 0;
    while (x * x <= n) {
      if (n % x == 0) {
        numOfFactors++;
        n /= x;
      } else
        x++;
    }
    if (x > 1)
      numOfFactors++;
    return numOfFactors;
  }

  static long fermat (long n) {
    if (n % 2 == 0)
      return 2;
    long x = (long)(Math.sqrt(n)), y = 0;
    long r = x * x - y * y - n;
    while (r != 0) {
      if (r < 0) {
        r += x + x + 1;
        x++;
      } else {
        r -= y + y + 1;
        y++;
      }
    }
    return x != y ? x - y : x + y;
  }

  static ArrayList<Integer> getDivisors (int n) {
    ArrayList<Integer> ret = new ArrayList<Integer>();
    for (int d = 1; d * d <= n; d++) {
      if (n % d == 0) {
        ret.add(d);
        if (d * d != n)
          ret.add(n / d);
      }
    }
    Collections.sort(ret);
    return ret;
  }

  static int eulerPhiDirect (int n) {
    int result = n;
    for (int i = 2; i <= n; i++) {
      if (isPrime(i))
        result -= result / i;
    }
    return result;
  }

  static long gcd (long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  static long multmod (long a, long b, long m) {
    long x = 0, y = a % m;
    for (; b > 0; b >>= 1) {
      if ((b & 1) == 1)
        x = (x + y) % m;
      y = (y << 1) % m;
    }
    return x % m;
  }

  static long randLong (long n) {
    return (long)(Math.random() * n);
  }

  static long brent (long n) {
    if (n % 2 == 0)
      return 2;
    long y = randLong(n - 1) + 1;
    long c = randLong(n - 1) + 1;
    long m = randLong(n - 1) + 1;
    long g = 1, r = 1, q = 1, ys = 0, hi = 0, x = 0;
    while (g == 1) {
      x = y;

      for (int i = 0; i < r; i++)
        y = (multmod(y, y, n) + c) % n;

      for (long k = 0; k < r && g == 1; k += m) {
        ys = y;
        hi = Math.min(m, r - k);
        for (int j = 0; j < hi; j++) {
          y = (multmod(y, y, n) + c) % n;
          q = multmod(q, x > y ? x - y : y - x, n);
        }
        g = gcd(q, n);
      }

      r *= 2;
    }

    if (g == n)
      do {
        ys = (multmod(ys, ys, n) + c) % n;
        g = gcd(x > ys ? x - ys : ys - x, n);
      } while (g <= 1);

    return g;
  }

  static ArrayList<Long> getDivisorsBig (long n) {
    ArrayList<Long> ret = new ArrayList<Long>();
    if (n <= 0)
      return ret;

    if (n == 1) {
      ret.add(1L);
      return ret;
    }

    for (; n % 2 == 0; n /= 2)
      ret.add(2L);

    for (; n % 3 == 0; n /= 3)
      ret.add(3L);

    int max = 1000000;

    for (long i = 5, w = 2; i <= max; i += w, w = 6 - w)
      for (; n % i == 0; n /= i)
        ret.add(i);

    for (long p = 0, p1; n > max; n /= p1) {
      for (p1 = n; p1 != p; p1 = brent(p)) {
        p = p1;
      }
      ret.add(p1);
    }

    if (n != 1)
      ret.add(n);

    Collections.sort(ret);
    return ret;
  }

  public static void main (String[] args) {
    System.out.println(getDivisorsBig(1000003l * 137 * 37 * 100003).toString());
    ArrayList<Integer> ret1 = getPrimesEratosthenes(123456789);
    ArrayList<Integer> ret2 = getPrimesLinear(123456789);
    ArrayList<Integer> ret3 = getPrimesAtkins(123456789);

    assert ret1.equals(ret2) && ret2.equals(ret3);
  }
}
