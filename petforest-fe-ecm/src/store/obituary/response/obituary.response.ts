type PetInformation = {
  name: string;
  endDate: string;
  age: number;
  petType: {
    id: number;
    name: string;
  };
  petBreed: {
    id: number;
    name: string;
  };
  petImage: {
    id: number;
    key: string;
    fileName: string;
  } | null;
};

type FuneralInformation = {
  funeralDate: string | null;
  funeralTime: string | null;
};

type GuardianInformation = {
  name: string;
  phoneNumber: string;
  address: string | null;
  addressDetail: string | null;
};

type Branch = {
  id: number;
  name: string;
  naverMap: string;
  kakaoMap: string;
  roadAddress: string;
  streetAddress: string;
};

type Reservation = {
  branch: Branch;
  guardianInformation: GuardianInformation;
  funeralInformation: FuneralInformation;
  petInformation: PetInformation;
};

type FileType = {
  fileName: string;
  key: string;
  id: number;
};

type ObituaryMessage = {
  author: string;
  content: string;
  createdAt: string;
  id: number;
};

export type ObituaryRESP = {
  id: number;
  link: string;
  createdAt: string;
  files: FileType[];
  obituaryMessages: ObituaryMessage[];
  reservation: Reservation;
  isPaymentActivated: boolean;
};

export type ObituaryMessagesRESP = {
  id: number;
  author: string;
  content: string;
  createdAt: string;
};

//Payment
export type ObituaryCreateCouponRESP = {
  id: number;
  code: string;
  paymentCode: string;
};

export type ObituaryMobileRegisterRESP = {
  status: string;
  additionalData: {
    Message: string;
    approvalKey: string;
    PayUrl: string;
    hashData: string;
    traceNo: string;
    paymentMethod: string;
    request_URI: string;
    Code: string;
  };
};
