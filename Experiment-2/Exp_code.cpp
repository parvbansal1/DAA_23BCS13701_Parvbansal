class Solution {
    public double myPow(double x, int n) {
        long pow = n;   // use long to handle -2^31
        if (pow < 0) {
            x = 1 / x;
            pow = -pow;
        }

        double ans = 1;
        while (pow > 0) {
            if (pow % 2 == 1) {  // if power is odd
                ans *= x;
            }
            x *= x;   // square the base
            pow /= 2; // reduce power
        }
        return ans;
    }
}
