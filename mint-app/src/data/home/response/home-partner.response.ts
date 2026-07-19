export type HomePartnersRESP = {
  data: HomePartner[];
};
export type HomePartner = {
  id: number;
  name: string;
  url: string;
  key: string;
};
export type TestimonialsRESP = {
  data: Testimonials[];
};
export type Testimonials = {
  id: number;
  key: string;
  url: string;
  commentInEnglish: string;
  commentInKorean: string;
  nameInEnglish: string;
  nameInKorean: string;
  roleInEnglish: string;
  roleInKorean: string;
};

export type Board = {
  id: number;
  key: string;
  url: string;
  departmentInEnglish: string;
  departmentInKorean: string;
  nameInEnglish: string;
  nameInKorean: string;
  roleInEnglish: string;
  roleInKorean: string;
};
export type BoardRESP = {
  data: Board[];
};
