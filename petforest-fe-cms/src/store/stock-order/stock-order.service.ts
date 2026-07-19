import { STOCK_ORDER_MATERIAL } from '@/utils/enums/funeral/stock-mngt.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { orNull, orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { StockOrderListDTO } from './dto/stock-order.dto';
import { StockOrderListFilter } from './request/stock-order.request';
import { StockOrderListRESP } from './response/stock-order.response';

export const stockOrderListParamsToFilter = (searchParams: URLSearchParams): StockOrderListFilter => {
  const { start, end } = {
    start: parseStringToNumber(searchParams.get('gte')),
    end: parseStringToNumber(searchParams.get('lte')),
  };

  let filter;
  if (start && end) {
    filter = JSON.stringify({ 'funeralDate.gte': start, 'funeralDate.lte': end });
  }

  return {
    filter: filter,
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  };
};

export const stockOrderRespToDto = (resp: StockOrderListRESP): StockOrderListDTO => {
  const dto: StockOrderListDTO = {
    ...resp,
    key: resp.id,
    applicationDate: resp.applicationDate ? dayjs(+resp.applicationDate) : null,
    funeralDate: resp.funeralDate ? dayjs(+resp.funeralDate) : null,
    material: resp.material as STOCK_ORDER_MATERIAL,
    stockOrderStatus: resp.stockOrderStatus as ENUM_STATUS,

    //materials
    columbarium: orNull(resp.columbarium),
    home: orNull(resp.home),
    homeColumbarium: orNull(resp.homeColumbarium),
    emotional: orNull(resp.emotional),
    sophisticated: orNull(resp.sophisticated),
    monite: orNull(resp.monite),
    frame: orNull(resp.frame),

    //dates
    // contactFrequencyFirst: dayjs(resp.contactFrequencyFirst).isValid()
    //   ? parseDate(resp.contactFrequencyFirst)
    //   : orString(resp.contactFrequencyFirst),
    // contactFrequencySecond: dayjs(resp.contactFrequencySecond).isValid()
    //   ? parseDate(resp.contactFrequencySecond)
    //   : orString(resp.contactFrequencySecond),
    // contactFrequencyThird: dayjs(resp.contactFrequencyThird).isValid()
    //   ? parseDate(resp.contactFrequencyThird)
    //   : orString(resp.contactFrequencyThird),
  };
  return dto;
};
