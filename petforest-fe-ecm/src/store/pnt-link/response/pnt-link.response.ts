import { ROOM_TYPE } from '@/utils/enums/pnt.enum';

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

export type PntDetailRESP = {
  id: number;
  name: ROOM_TYPE;
  reservation: {
    id: number;
    code: string;
    funeralInformation: {
      funeralDate: string;
      funeralTime: string;
    };
    reservationPhoto: {
      files: [
        {
          id: number;
          key: string;
          fileName: string;
          usedInMemorialRoom: boolean;
        },
        {
          id: number;
          key: string;
          fileName: string;
          usedInMemorialRoom: boolean;
        },
        {
          id: number;
          key: string;
          fileName: string;
          usedInMemorialRoom: boolean;
        },
      ];
    };
    petInformation: {
      name: string;
      petBreed: {
        id: number;
        name: string;
      } | null;
    };
    guardianInformation: {
      name: string;
    };
  };
};
