class Solution {
     public static int knap(int val[],int wt[],int W,int dp[][],int n){
        if(W==0 || n==0) return 0;
        
        if(dp[n-1][W]!=-1) return  dp[n-1][W];
        
        if(wt[n-1]<=W){
            int a = val[n-1] + knap(val,wt,W-wt[n-1],dp,n-1);
            
            int b = knap(val,wt,W,dp,n-1);
            
            return dp[n-1][W] = Math.max(a,b);
        }
        else{
            return dp[n-1][W] =  knap(val,wt,W,dp,n-1);
        }
    }
    static int knapsack(int W, int val[], int wt[]) {
        // code here
        int dp[][] = new int[val.length+1][W+1];
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                dp[i][j] = -1;
            }
        }
        return knap(val,wt,W,dp,val.length);
    }
}
