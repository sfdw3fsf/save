export type FormDataType = {
  reserveInfo: {
    branch: number;
    date: string;
    time: string;
  };
  petInfo: {
    petName: string;
    petWeight: number;
    petClassification: string;
  };
  guardianInfo: {
    guardianName: string;
    numOfVisitor: string;
    phoneNumber: string;
    message: string;
  };
  additionalInfo: {
    isNotSellected: boolean | null;
    funeralService: boolean;
    flowerBouquetService: { isActive: boolean; accompanny: string | null; address: string; detailedAddress: string };
    lucetteService: boolean;
  };
};
