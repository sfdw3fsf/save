import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import nProgress from 'nprogress';

const nProgressHandler = (type: 'start' | 'stop') => {
  if (typeof window !== 'object') return;
  if (type === 'start') nProgress.start();
  else nProgress.done();
};

export class BaseHTTP {
  public static _instance: BaseHTTP;
  public axios: AxiosInstance;
  constructor() {
    this.axios = axios.create({
      baseURL: process.env.NEXT_PUBLIC_API_URL,
    });

    this.axios.interceptors.request.use(
      async (config: AxiosRequestConfig): Promise<any> => {
        nProgressHandler('start');

        config.headers = {
          'branch-id': '0',
          ...config.headers,
        };

        return config;
      },
      (error: AxiosError): Promise<AxiosError> => {
        return Promise.reject(error);
      },
    );

    this.axios.interceptors.response.use(
      (response: AxiosResponse): AxiosResponse => {
        nProgressHandler('stop');
        return response;
      },
      (error: AxiosError): Promise<AxiosError> => {
        if ([401].includes(error.response?.status || 0)) {
          localStorage.clear();
        }
        nProgressHandler('stop');
        return Promise.reject(error);
      },
    );
  }

  public static getInstance() {
    if (!BaseHTTP._instance) {
      BaseHTTP._instance = new BaseHTTP();
    }
    return BaseHTTP._instance;
  }

  public config(config: any) {
    const { accessToken, branchId } = config;
    api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
    api.defaults.headers.common['branch-id'] = branchId;
  }

  public static reset() {
    BaseHTTP._instance = new BaseHTTP();
  }
  /*
   Corning (GLW), one of the world's leading innovators in materials science, has been the key ------- of the glass used on iPhones since the very beginning.
   CPBJ Top 50 Fastest Growing Companies program ranked the nominations according to revenue ------- over the three-year period.
   The ‘Queen of Crime’, Katarzyna Bonda, has so far published four crime novels ------- police psychologist Hubert Meyer as the main character.
   113. During the course of the project, the contractor must file a ------- report every 60 days.
120. Job seekers are ------- more likely to receive a response from recruiters if they can customize their resume to each position they apply to.

(A) much

(B) very

(C) rarely

(D) along
122. SpendingPulse provides insightful data on overall retail sales across all ------- types including cash and check.

(A) payment

(B) bill

(C) credit

(D) bank
123. Marathon Asset Management announced today it has entered ------- an agreement to acquire Kaléo Inc., a Richmond-based pharmaceutical company.

(A) of

(B) into

(C) at

(D) yet
125. Running out of hot water is the most commonly reported ------- faced by lower end hotel guests.

(A) issue

(B) complaint

(C) doubt

(D) symptom

128. Erwin Schrödinger is one of the greatest theoretical physicists whose papers were published in various notable scientific -------.

(A) periodicals

(B) novels

(C) catalogs

(D) directories
30. To help attendees choose suitable accommodation, we have attached a summary of estimated expenses for lodging and dinners, ------- breakfast and lunch are included in hotel rates.

(A) furthermore

(B) assuming that

(C) regardless of

(D) as a result
130. To help attendees choose suitable accommodation, we have attached a summary of estimated expenses for lodging and dinners, ------- breakfast and lunch are included in hotel rates.

(A) furthermore

(B) assuming that

(C) regardless of

(D) as a result
  */
}

export const api = BaseHTTP.getInstance().axios;
