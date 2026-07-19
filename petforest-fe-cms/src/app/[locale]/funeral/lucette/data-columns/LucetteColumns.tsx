import CustomButton from '@/components/common/CustomButton';
import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import CustomTimePicker from '@/components/common/CustomTimePicker';
import Status from '@/components/common/Status';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import useResize from '@/hook/useResize';
import { LucetteListDTO } from '@/store/funeral/lucette/dto/lucette.dto';
import { LucetteListUpdateREQ } from '@/store/funeral/lucette/request/lucette.request';
import { MaterialNameRESP } from '@/store/material/response/material.response';
import { ICONS } from '@/theme';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter, helperOption } from '@/utils/helpers/common.helper';
import { orUndefined } from '@/utils/parser/io.parser';
import { FilterOptions } from '@/utils/types/option.type';
import {
  handleBlurUpdate,
  handleChangeDateUpdate,
  handleChangeUpdate,
  handleNumericUpdate,
} from '@/utils/utils/inline-update.util';
import { PlusOutlined } from '@ant-design/icons';
import { TableColumnsType } from 'antd';
import dayjs, { Dayjs } from 'dayjs';
import Image from 'next/image';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

type LucetteColumnsProps = {
  onRecordSelect: (id: number) => void;
  onListUpdate: (id: number, body: LucetteListUpdateREQ) => void;
  managerOptions: FilterOptions;
  materialNames: MaterialNameRESP[];
  breedNameOptions: FilterOptions<string>;
  onDownloadPdf: (id: number) => void;
};

export const LucetteColumns = ({
  onRecordSelect,
  onListUpdate,
  managerOptions,
  materialNames,
  breedNameOptions,
  onDownloadPdf,
}: LucetteColumnsProps): TableColumnsType<LucetteListDTO> => {
  const { t: tLucette } = useTranslation('funeral_lucette');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');

  const { isResponsive } = useResize();

  const statusFilter = helperFilter('status', ENUM_STATUS, tEnum);
  const categoryFilter = helperFilter('lucette.category', LUCETTE_CATEGORY, tEnum);
  const categoryOptions = helperOption('lucette.category', LUCETTE_CATEGORY, tEnum);
  const statusOptions = helperOption('status', ENUM_STATUS, tEnum);

  const columns = useMemo<TableColumnsType<LucetteListDTO>>(
    () => [
      {
        title: <EditableHeader title={tLucette('lucette.table.status')} />,
        dataIndex: 'status',
        key: 'status',
        filters: statusFilter,
        width: 130,
        sorter: true,
        render: (value: ENUM_STATUS, record) => (
          <CustomSelect
            onChange={handleChangeUpdate<LucetteListUpdateREQ>(record.id, 'status', record.status, onListUpdate)}
            variant='borderless'
            optionRender={(option) => option.data.text}
            options={statusFilter.map((option) => ({
              ...option,
              label: <Status isSuccess={option.value === ENUM_STATUS.COMPLETE} status={option.text} />,
            }))}
            defaultValue={value}
          />
        ),
        ...(isResponsive ? {} : { fixed: 'left' as const }),
      },
      {
        title: tLucette('lucette.table.id'),
        dataIndex: 'reservationCode',
        width: 120,
        sorter: true,
        render: (value: string, record: LucetteListDTO) => <ReservationCode code={value} id={record.id} />,
        ...(isResponsive ? {} : { fixed: 'left' as const }),
      },
      {
        title: tLucette('lucette.table.date'),
        dataIndex: 'funeralDate',
        sorter: true,
        width: 100,
        ...(isResponsive ? {} : { fixed: 'left' as const }),
      },
      {
        title: <EditableHeader title={tLucette('lucette.table.process_date')} />,
        dataIndex: 'processDate',
        width: 120,
        sorter: true,
        render: (value: Dayjs, record: LucetteListDTO) => (
          <CustomDatePicker
            defaultValue={value}
            allowClear={false}
            onChange={handleChangeDateUpdate<LucetteListUpdateREQ>(record.id, 'processDate', onListUpdate)}
            minDate={dayjs(record.funeralDate)}
          />
        ),
      },
      {
        title: <EditableHeader title={tLucette('lucette.table.process_time')} />,
        dataIndex: 'processTime',
        width: 100,
        sorter: true,
        render: (value: Dayjs, record: LucetteListDTO) => {
          return (
            <CustomTimePicker
              format={DATE_FORMAT.TIME.PICKER_HOUR_MIN}
              value={value}
              allowClear={false}
              onChange={(data) => {
                onListUpdate(record.id, { processTime: orUndefined(data?.format(DATE_FORMAT.TIME.PICKER_HOUR_MIN)) });
              }}
            />
          );
        },
      },
      {
        title: <EditableHeader title={tLucette('lucette.table.category_column')} />,
        dataIndex: 'process',
        filters: categoryFilter,
        width: 100,
        render: (value: LUCETTE_CATEGORY, record: LucetteListDTO) => (
          <CustomSelect
            onChange={handleChangeUpdate<LucetteListUpdateREQ>(record.id, 'process', record.process, onListUpdate)}
            variant='borderless'
            options={categoryOptions}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tLucette('lucette.table.manager')} />,
        dataIndex: 'manager.fullName', // managerName
        filters: managerOptions.map((i) => ({ ...i, value: i.text })),
        width: 120,
        sorter: true,
        render: (_, record: LucetteListDTO) => (
          <CustomSelect
            onChange={handleChangeUpdate<LucetteListUpdateREQ>(record.id, 'managerId', record.managerId, onListUpdate)}
            variant='borderless'
            options={managerOptions}
            defaultValue={record.managerId}
          />
        ),
      },
      {
        title: tLucette('lucette.table.guardian'),
        dataIndex: 'guardianName',
        width: 100,
        sorter: true,
      },
      {
        title: tLucette('lucette.table.pet_name'),
        dataIndex: 'petName',
        width: 100,
        sorter: true,
      },
      {
        title: tLucette('lucette.table.animal_breed'),
        dataIndex: 'reservation.petInformation.petBreed.name', // petBreed
        filters: breedNameOptions,
        width: 100,
        sorter: true,
        render: (_, record: LucetteListDTO) => record.petBreed,
      },
      {
        title: tLucette('lucette.table.weight'),
        dataIndex: 'petWeight',
        sorter: true,
        width: 70,
      },
      {
        title: <EditableHeader title={tLucette('lucette.table.amount_remain')} />,
        dataIndex: 'boneQuantity',
        sorter: true,
        width: 100,
        render: (value, record: LucetteListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<LucetteListUpdateREQ>(record.id, 'boneQuantity', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader className='justify-center' title={tLucette('lucette.table.usage_mold')} />,
        width: 200,
        children: materialNames.map((item) => ({
          title: item.name,
          dataIndex: ['lucetteOrder', item.id],
          width: 70,
          key: item.id,
          render: (value, record: LucetteListDTO) => (
            <CustomNumericFormat
              onBlur={handleNumericUpdate<LucetteListUpdateREQ>(
                record.id,
                'material',
                value,
                onListUpdate,
                undefined,
                (value: number) => ({ materialId: +item.id, quantity: value }),
              )}
              defaultValue={value}
            />
          ),
        })),
      },
      {
        title: tLucette('lucette.table.noti_talk'),
        dataIndex: 'notificationStatus',
        sorter: true,
        width: 150,
        filters: statusFilter,
        render: (status: ENUM_STATUS) =>
          status && <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}`, status)} />,
      },
      {
        title: <EditableHeader title={tLucette('lucette.table.remark')} />,
        dataIndex: 'note',
        width: 150,
        render: (value: string, record: LucetteListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<LucetteListUpdateREQ>(record.id, 'note', value, onListUpdate)}
            type={InputEnum.TEXT_AREA}
            defaultValue={value}
          />
        ),
      },
      {
        title: <div className='text-center'>{tLucette('lucette.table.fair_warranty')}</div>,
        width: 100,
        align: 'center',
        render: (_, record: LucetteListDTO) => (
          <CustomButton
            onClick={() => {
              onDownloadPdf(record.id);
            }}
            type='text'
            icon={<Image src={ICONS.DOWNLOAD} alt='Download' width={24} height={24} />}
          />
        ),
      },
      {
        title: <div className='text-center'>{tLucette('lucette.table.info')}</div>,
        key: 'look',
        width: 120,
        align: 'center',
        render: (_, record: LucetteListDTO) => {
          return (
            <>
              {record.jewelry ? (
                <CustomButton
                  onClick={() => onRecordSelect(record.id)}
                  type='text'
                  icon={<Image src={ICONS.LOOK} alt='Look' width={24} height={24} />}>
                  {tCommon('table.view')}
                </CustomButton>
              ) : (
                <CustomButton
                  onClick={() => onRecordSelect(record.id)}
                  type='ghost'
                  className='text-primary-1000 !border-none'
                  icon={<PlusOutlined />}>
                  {tCommon('button.add')}
                </CustomButton>
              )}
            </>
          );
        },
      },
    ],
    [isResponsive, managerOptions, materialNames, breedNameOptions],
  );

  return columns;
};
