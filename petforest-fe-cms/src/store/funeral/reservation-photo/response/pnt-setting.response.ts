export type PntSettingListRESP = {
  id: number;
  name: string | null;
  reservation: {
    id: number;
    code: string | null;
    funeralInformation: {
      funeralDate: string | null;
      funeralTime: string | null;
    };
    reservationPhoto: {
      files: {
        id: number;
        key: string;
        fileName: string;
        usedInMemorialRoom: boolean;
      }[];
    };
    petInformation: {
      name: string | null;
      petBreed: {
        id: number;
        name: string | null;
      } | null;
      petImage: {
        id: number;
        key: string;
        fileName: string | null;
      } | null;
    };
    guardianInformation: {
      name: string | null;
    };
  } | null;
};

export type PntSettingDetailRESP = {
  id: number;
  name: string | null;
  reservation: {
    id: number;
    code: string | null;
    funeralInformation: {
      funeralDate: string | null;
      funeralTime: string | null;
    };
    reservationPhoto: {
      files: {
        id: number;
        key: string;
        fileName: string | null;
        usedInMemorialRoom: boolean | null;
      }[];
    };
    petInformation: {
      name: string | null;
      petBreed: {
        id: number;
        name: string | null;
      } | null;
    };
    guardianInformation: {
      name: string | null;
    };
  } | null;
};

export type PntSettingDetailCreateRESP = {
  id: number;
};
