export type StatisticBasicREQ = {
  startDate: number;
  endDate: number;
  branchIds: number[];
  funeralServiceNames?: string[];
  petTypeIds?: number[];
};

export type StatisticRegionREQ = {
  startDate: number;
  endDate: number;
  branchIds: number[];
  regions?: string;
};

export type StatisticArticleREQ = {
  startDate: number;
  endDate: number;
  branchIds: number[];
  type?: string;
};

export type StatisticServiceFilterREQ = {
  branchIds: number[];
};
