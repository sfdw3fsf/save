export type TimePickingType = {
  time: string;
  isAbleToReserve: boolean;
};

export type FormDataType = {
  reserveInfo: {
    branch: number | null;
    date: string;
    time: string | null;
  };
  petInfo: {
    petName: string;
    petWeight: number | null;
    petClassification: string | null;
  };
  guardianInfo: {
    guardianName: string;
    numOfVisitor: string | null;
    phoneNumber: string;
    message: string;
  };
  additionalInfo: {
    isNotSellected: boolean | null;
    funeralService: boolean;
    flowerBouquetService: { accompanny: string | null; address: string; detailedAddress: string };
    lucetteService: boolean;
  };
};
