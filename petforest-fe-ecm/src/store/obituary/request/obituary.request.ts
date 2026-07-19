export type ObituaryUpdateREQ = {
  petImageId?: number;
  petName: string;
  petTypeId: number;
  endDate: number | undefined;
  petAge: number;
  petBreedId: number;
  obituaryFileIds: number[];
  isPaymentActivated: boolean;
};

export type ObituaryMessageCreateREQ = {
  author: string;
  content: string;
  password?: string;
};

export type ObituaryMessageValidateREQ = {
  password: string;
};

//Payment
export type ObituaryCouponCreateREQ = {
  purchaser: string;
  purchaserContact: string;
  amount: number;
};

export type ObituaryMobileRegisterREQ = {
  orderId: string;
  productPrice: string;
  productName: string;
  payMethod: 'CARD';
  Ret_URL: 'https://pet-forest-api.wala-dev.com/api/v1/ecm/obituaries/payments/confirm';
  currency: '410';
};
