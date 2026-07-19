export type ReservationPhotoListRESP = {
  id: number;
  files: {
    id: number;
    key: string;
    fileName: string | null;
    usedInMemorialRoom: boolean | null;
  }[];
  reservation: {
    code: string | null;
    leader: {
      id: number;
      fullName: string | null;
    };
    funeralInformation: {
      funeralDate: string | null;
      funeralTime: string | null;
    };
    petInformation: {
      name: string | null;
      petBreed: {
        id: number;
        name: string | null;
      };
    };
    guardianInformation: {
      name: string | null;
      phoneNumber: string | null;
    };
  } | null;
};

export type ReservationPhotoDetailRESP = {
  id: number;
  files: {
    id: number;
    key: string;
    fileName: string | null;
    usedInMemorialRoom: boolean | null;
  }[];
  reservation: {
    code: string | null;
    leader: {
      id: number;
      fullName: string | null;
    };
    funeralInformation: {
      funeralDate: string | null;
      funeralTime: string | null;
    };
    petInformation: {
      name: string | null;
      petBreed: {
        id: number;
        name: string | null;
      };
    };
    guardianInformation: {
      name: string | null;
      phoneNumber: string | null;
    };
  } | null;
};
