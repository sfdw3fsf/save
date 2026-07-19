import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import Status from '@/components/common/Status';

import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import useResize from '@/hook/useResize';
import { useAuthStore } from '@/store/auth/auth.store';
import { StockOrderListDTO, StockOrderListSupply } from '@/store/stock-order/dto/stock-order.dto';
import { StockOrderListUpdateREQ } from '@/store/stock-order/request/stock-order.request';
import { BRANCH_ENUMS } from '@/utils/enums/common.enums';
import { STOCK_ORDER_MATERIAL } from '@/utils/enums/funeral/stock-mngt.enum';
import { InputEnum } from '@/utils/enums/input.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter, helperGetBranchEnum, helperOption } from '@/utils/helpers/common.helper';
import {
  handleBlurUpdate,
  handleChangeDateUpdate,
  handleChangeUpdate,
  handleNumericUpdate,
} from '@/utils/utils/inline-update.util';
import { TableColumnsType, TableColumnType } from 'antd';
import dayjs from 'dayjs';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

type StockListColumnsProps = {
  onListUpdate: (id: number, body: StockOrderListUpdateREQ) => void;
};

export const StockListColumns = ({ onListUpdate }: StockListColumnsProps): TableColumnsType<StockOrderListDTO> => {
  const { t: tStock } = useTranslation('funeral_stock');
  const { t: tEnum } = useTranslation('enum');

  const { isResponsive } = useResize();
  const { selectedBranchId } = useAuthStore();

  const stockOrderColorFilter = useMemo(() => helperFilter('material', STOCK_ORDER_MATERIAL, tEnum), [tEnum]);
  const stockOrderColorOptions = useMemo(() => helperOption('material', STOCK_ORDER_MATERIAL, tEnum), [tEnum]);

  const statusFilter = useMemo(() => helperFilter('status', ENUM_STATUS, tEnum), [tEnum]);

  const allSupplyKeys: Record<string, (keyof StockOrderListSupply)[]> = {
    [BRANCH_ENUMS.GYEONGGI_GWANGJU]: ['homeColumbarium', 'home', 'emotional', 'sophisticated', 'frame'],
    [BRANCH_ENUMS.GIMPO]: ['columbarium', 'home', 'emotional', 'sophisticated', 'frame'],
    [BRANCH_ENUMS.NAMYANGJU]: ['home', 'emotional', 'sophisticated', 'monite', 'frame'],
    default: ['columbarium', 'home', 'emotional', 'sophisticated', 'monite', 'frame'],
  };

  const allSupplyColumns = useMemo<Record<keyof StockOrderListSupply, TableColumnType<StockOrderListDTO>>>(
    () => ({
      homeColumbarium: {
        title: tStock('table.funeral_supplies.children.home_columbarium'),
        dataIndex: ['homeColumbarium'],
        key: 'homeColumbarium',
        width: 128,
        sorter: true,
        render: (value: number, record: StockOrderListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<StockOrderListUpdateREQ>(record.id, 'homeColumbarium', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      columbarium: {
        title: tStock('table.funeral_supplies.children.columnbarium'),
        dataIndex: ['columbarium'],
        key: 'columbarium',
        width: 134,
        sorter: true,
        render: (value: number, record: StockOrderListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<StockOrderListUpdateREQ>(record.id, 'columbarium', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      home: {
        title: tStock('table.funeral_supplies.children.housing_home'),
        dataIndex: ['home'],
        key: 'home',
        width: 134,
        sorter: true,
        render: (value: number, record: StockOrderListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<StockOrderListUpdateREQ>(record.id, 'home', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      emotional: {
        title: tStock('table.funeral_supplies.children.preciousness_memory_warmth'),
        dataIndex: ['emotional'],
        key: 'emotional',
        width: 146,
        sorter: true,
        render: (value: number, record: StockOrderListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<StockOrderListUpdateREQ>(record.id, 'emotional', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      sophisticated: {
        title: tStock('table.funeral_supplies.children.eternity_elegance'),
        dataIndex: ['sophisticated'],
        key: 'sophisticated',
        width: 121,
        sorter: true,
        render: (value: number, record: StockOrderListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<StockOrderListUpdateREQ>(record.id, 'sophisticated', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      frame: {
        title: tStock('table.funeral_supplies.children.frame'),
        dataIndex: ['frame'],
        key: 'frame',
        width: 162,
        sorter: true,
        render: (value: number, record: StockOrderListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<StockOrderListUpdateREQ>(record.id, 'frame', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      monite: {
        title: tStock('table.funeral_supplies.children.monite'),
        dataIndex: ['monite'],
        key: 'monite',
        width: 134,
        sorter: true,
        render: (value: number, record: StockOrderListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<StockOrderListUpdateREQ>(record.id, 'monite', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
    }),
    [tStock],
  );
  const supplyColumns = useMemo<TableColumnType<StockOrderListDTO>[]>(() => {
    const branch: BRANCH_ENUMS | null = helperGetBranchEnum(selectedBranchId);
    const keys = allSupplyKeys[branch ?? 'default'];

    return keys.map((supply, index) => {
      return {
        ...allSupplyColumns[supply],
        title: `${index + 1}.${allSupplyColumns[supply].title}`,
        width: index === 0 ? 128 : index === 1 ? 131 : index === 2 ? 177 : index === 3 ? 137 : 83,
      };
    });
  }, [selectedBranchId]);

  const columns = useMemo<TableColumnsType<StockOrderListDTO>>(
    () => [
      {
        title: <EditableHeader title={tStock('table.application_status.title')} />,
        dataIndex: 'stockOrderStatus',
        key: 'stockOrderStatus',
        filters: statusFilter,
        width: 120,
        sorter: true,
        ...(isResponsive ? {} : { fixed: 'left' as const }),
        render: (value: ENUM_STATUS, record: StockOrderListDTO) => (
          <CustomSelect
            onChange={handleChangeUpdate<StockOrderListUpdateREQ>(
              record.id,
              'stockOrderStatus',
              record.stockOrderStatus,
              onListUpdate,
            )}
            variant='borderless'
            optionRender={(option) => option.data.text}
            options={statusFilter.map((option) => ({
              ...option,
              label: <Status isSuccess={option.value === ENUM_STATUS.COMPLETE} status={option.text} />,
            }))}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tStock('table.application_date')} />,
        ...(isResponsive ? {} : { fixed: 'left' as const }),
        dataIndex: 'applicationDate',
        key: 'applicationDate',
        sorter: true,
        width: 120,
        render: (value: dayjs.Dayjs, record: StockOrderListDTO) => (
          <CustomDatePicker
            defaultValue={value}
            onChange={handleChangeDateUpdate<StockOrderListUpdateREQ>(record.id, 'applicationDate', onListUpdate)}
          />
        ),
      },
      {
        title: <EditableHeader title={tStock('table.funeral_date')} />,
        ...(isResponsive ? {} : { fixed: 'left' as const }),
        dataIndex: 'funeralDate',
        key: 'funeralDate',
        width: 120,
        sorter: true,
        render: (value: dayjs.Dayjs, record: StockOrderListDTO) => (
          <CustomDatePicker
            defaultValue={value}
            onChange={handleChangeDateUpdate<StockOrderListUpdateREQ>(record.id, 'funeralDate', onListUpdate)}
          />
        ),
      },
      {
        title: tStock('table.funeral_number'),
        ...(isResponsive ? {} : { fixed: 'left' as const }),
        dataIndex: ['reservation', 'code'],
        key: 'reservation.code',
        width: 110,
        sorter: true,
        render: (value: string, record: StockOrderListDTO) => <ReservationCode code={value} id={record.reservation?.id} />,
      },
      {
        title: <EditableHeader title={tStock('table.guardian')} />,
        dataIndex: 'guardianName',
        key: 'guardianName',
        sorter: true,
        width: 100,
        render: (value: string, record: StockOrderListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'guardianName', value, onListUpdate)}
            type={InputEnum.TEXT}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tStock('table.pet_name')} />,
        dataIndex: 'childName',
        key: 'childName',
        width: 130,
        sorter: true,
        render: (value: string, record: StockOrderListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'childName', value, onListUpdate)}
            type={InputEnum.TEXT}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tStock('table.guardian_contact')} />,
        dataIndex: 'guardianContact',
        key: 'guardianContact',
        width: 130,
        sorter: true,
        render: (value: string, record: StockOrderListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'guardianContact', value, onListUpdate)}
            type={InputEnum.PHONE}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tStock('table.enshrinement_location')} />,
        dataIndex: 'columbariumPlace',
        key: 'columbariumPlace',
        sorter: true,
        width: 100,
        render: (value: string, record: StockOrderListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'columbariumPlace', value, onListUpdate)}
            type={InputEnum.TEXT}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tStock('table.color.title')} />,
        filters: stockOrderColorFilter,
        dataIndex: 'material',
        key: 'material',
        sorter: true,
        width: 100,
        render: (value: STOCK_ORDER_MATERIAL, record: StockOrderListDTO) => (
          <CustomSelect
            onChange={handleChangeUpdate<StockOrderListUpdateREQ>(record.id, 'material', record.material, onListUpdate)}
            variant='borderless'
            options={stockOrderColorOptions}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader className='justify-center' title={tStock('table.funeral_supplies.title')} />,
        children: supplyColumns,
      },
      {
        title: <EditableHeader className='justify-center' title={tStock('table.contact_method_frequency.title')} />,
        children: [
          {
            title: `1${tStock('table.contact_method_frequency.car')}`,
            dataIndex: ['contactFrequencyFirst'],
            key: 'contactFrequencyFirst',
            width: 80,
            sorter: true,
            render: (value: string, record: StockOrderListDTO) => (
              <CustomTextInput
                onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'contactFrequencyFirst', value, onListUpdate)}
                type={InputEnum.TEXT}
                defaultValue={value}
              />
            ),
          },
          {
            title: `2${tStock('table.contact_method_frequency.car')}`,
            dataIndex: ['contactFrequencySecond'],
            key: 'contactFrequencySecond',
            width: 80,
            sorter: true,
            render: (value: string, record: StockOrderListDTO) => (
              <CustomTextInput
                onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'contactFrequencySecond', value, onListUpdate)}
                type={InputEnum.TEXT}
                defaultValue={value}
              />
            ),
          },
          {
            title: `3${tStock('table.contact_method_frequency.car')}`,
            dataIndex: ['contactFrequencyThird'],
            key: 'contactFrequencyThird',
            width: 80,
            sorter: true,
            render: (value: string, record: StockOrderListDTO) => (
              <CustomTextInput
                onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'contactFrequencyThird', value, onListUpdate)}
                type={InputEnum.TEXT}
                defaultValue={value}
              />
            ),
          },
        ],
      },
      {
        title: <EditableHeader title={tStock('table.remark')} />,
        dataIndex: 'note',
        key: 'note',

        render: (value: string, record: StockOrderListDTO) => (
          <CustomTextInput
            className='min-w-[100px]'
            onBlur={handleBlurUpdate<StockOrderListUpdateREQ>(record.id, 'note', value, onListUpdate)}
            type={InputEnum.TEXT_AREA}
            defaultValue={value}
          />
        ),
      },
    ],
    [isResponsive, supplyColumns],
  );

  return columns;
};
