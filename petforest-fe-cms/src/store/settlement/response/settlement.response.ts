export type SettlementAdjustmentListRESP = {
  id: number;
  status: string;
  funeralDate: number | null;
  funeralId: number | null;
  funeralCode: string;
  basicAmount: number | null;
  productAmount: number | null;
  additionalCost: {
    transport: number | null;
    overweight: number | null;
    columbarium: number | null;
    temporaryPlacement: number | null;
    carbonPlate: number | null;
    otherAdditionalCost: number | null;
  };
  reasonOfDiscount: string | null;
  discountAmount: number | null;
  usageCoupon: number | null;
  remainingAmount: number | null;
  totalAmount: number | null;
  settlementMethod: {
    card: number | null;
    cash: number | null;
    accountAmount: number | null;
    prePaid: number | null;
  };
  invoiceMemo: string | null;
  memo: string | null;
  branchId: number | null;
};

export type SettlementAdjustmentSummaryRESP = {
  totalSettlements: number;
  totalAmount: number;
  totalRemainingAmount: number;
};

// Additional cost

export type SettlementAdditionalCostSummaryRESP = {
  total: number;
};

export type SettlementAdjustmentAdditionalCostListRESP = {
  id: number;
  reservationId: number;
  status: string;
  funeralDate: number;
  basicAmount: number;
  transport: number;
  overweight: number;
  columbarium: number;
  temporaryPlacement: number;
  carbonPlate: number;
  otherAdditionalCost: number;
};

// Coupon

export type SettlementCouponListRESP = {
  id: number;
  code: string | null;
  status: string;
  paymentCode: string | null;
  amount: number | null;
  note: string | null;
  purchaser: string | null;
  purchaserContact: string | null;
  purchaseDate: string | null; // epoch
  useDate: string | null; // epoch
  reservation: {
    id: number;
    code: string | null;
    funeralInformation: {
      funeralDate: string | null; // epoch
      funeralTime: string | null;
    } | null;
    guardianInformation: {
      name: string | null;
      phoneNumber: string | null;
    } | null;
  } | null;
};
