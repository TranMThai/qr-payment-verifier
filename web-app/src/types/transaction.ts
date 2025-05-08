export type Transaction = {
    id: string;
    bankBrandName: string;
    accountNumber: string;
    transactionDate: Date;
    amountOut: number;
    amountIn: number;
    accumulated: number;
    transactionContent: string;
    referenceNumber: string;
    code: string;
    subAccount: string;
    bankAccountId: string;
    isRead: boolean;
  };
  