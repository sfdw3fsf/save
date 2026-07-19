declare module 'rellax' {
  interface RellaxOptions {
    speed?: number;
    breakpoints?: {
      [key: number]: number;
    };
    wrapper?: HTMLElement;
    [key: string]: any;
  }

  export default class Rellax {
    constructor(elements: string | HTMLElement | HTMLElement[], options?: RellaxOptions);
    refresh(): void;
    destroy(): void;
  }
}
