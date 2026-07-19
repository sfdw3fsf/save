export type StockOrderListRESP = {
  id: number;
  stockOrderStatus: string;
  applicationDate: string | null;
  funeralDate: string | null;
  guardianName: string;
  childName: string;
  guardianContact: string;
  columbariumPlace: string;
  material: string;
  columbarium?: number;
  home?: number;
  homeColumbarium?: number;
  emotional?: number;
  sophisticated?: number;
  monite?: number;
  frame?: number;
  note: string;
  contactFrequencyFirst?: string;
  contactFrequencySecond?: string;
  contactFrequencyThird?: string;
  reservation?: {
    id: number;
    code: string;
  };
};
