import CustomIconButton from '@/components/common/CustomIconButton';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { useMaterial } from '@/hook/material/useMaterial';
import { useSettingService } from '@/hook/setting/useSettingService';
import { nameIdValueToOptions } from '@/store/common/common.service';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { getMaterialListApi, materialFuneralKeys } from '@/store/material/material.api';
import { materialRespToDto } from '@/store/material/material.service';
import { getSettingServiceMaterialApi } from '@/store/setting/service/setting-service.api';
import { reservationInventoriesRespToFormDto } from '@/store/setting/service/setting-services.service';
import { ICONS } from '@/theme';
import { DETAIL_SERVICE_ATTENDANCE_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum'; // Ensure the path is correct
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { PlusCircleOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';
import { Divider } from 'antd';
import Image from 'next/image';
import { useEffect, useMemo, useRef } from 'react';
import { useFieldArray, useFormContext, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FRD_CollapseSelection from './FRD_CollapseSelection';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_Service = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const { t: tForm } = useTranslation('form');
  const {
    control,
    watch,
    setValue,
    getValues,
    formState: { errors },
  } = useFormContext<ReservationDetailDTO>();
  const serviceId = watch('funeralInfo.funeral');
  const materialId = useRef<number>(0);
  const { serviceOptions, settingServicesData } = useSettingService({});
  const { materialTypeOptions, materialGroupTypeNameData } = useMaterial();

  const { fields, prepend, remove } = useFieldArray({
    control,
    name: 'serviceInfo.supplies',
  });

  const { mutate: mutateChangeFuneralServiceMaterials } = useMutation({
    mutationFn: (value: number) => getSettingServiceMaterialApi(value),
    onSuccess: (response) => {
      const reservationInventories = reservationInventoriesRespToFormDto(response?.data?.data || []);
      setValue('serviceInfo.supplies', reservationInventories ?? []);
    },
  });

  const { data: materialFuneralListData } = useQuery({
    queryKey: materialFuneralKeys.lists(),
    queryFn: () => getMaterialListApi({ materialType: MATERIAL_TYPE.FUNERAL }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(materialRespToDto) };
    },
    placeholderData: keepPreviousData,
    enabled: !materialId.current,
  });

  const ATTENDANCE_OPTIONS = helperOption('reservation.attendance', DETAIL_SERVICE_ATTENDANCE_OPTIONS, tEnum);

  const onAddSupply = () => {
    prepend({ type: '', kind: null, quantity: 0, amount: 0 });
  };

  const supplyTypes = useWatch({
    control,
    name: 'serviceInfo.supplies',
  });

  const totalAmount = useMemo(
    () => (supplyTypes ? supplyTypes.reduce((sum, item) => sum + (item.amount || 0), 0) : 0),
    [supplyTypes],
  );

  const funeralServicePrice = useMemo(() => {
    return settingServicesData?.data.find((item) => item.id === serviceId)?.price || 0;
  }, [supplyTypes]);

  const handleChangeMaterial = (index: number, selectedValue: number) => {
    materialId.current = selectedValue;
    const materialPrice = materialFuneralListData?.data.find((item) => item.id === selectedValue)?.price || 0;
    const quantity = getValues(`serviceInfo.supplies[${index}].quantity` as any);
    setValue(`serviceInfo.supplies[${index}].amount` as any, materialPrice * quantity);
    setValue(`serviceInfo.supplies[${index}].price` as any, materialPrice);
  };

  const handleChangeQuantity = (index: number, selectedValue: number) => {
    const materialPrice = getValues(`serviceInfo.supplies[${index}].price` as any);
    setValue(`serviceInfo.supplies[${index}].amount` as any, materialPrice * selectedValue);
  };

  useEffect(() => {
    setValue('paymentMethod.itemAmount', totalAmount);
  }, [totalAmount, setValue]);

  useEffect(() => {
    setValue('paymentMethod.baseAmount', funeralServicePrice);
  }, [funeralServicePrice, setValue]);

  const handleChangeFuneralService = (value: number) => {
    mutateChangeFuneralServiceMaterials(value);
  };

  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.service')} />
      <div className='flex gap-4 flex-col'>
        <div className='grid grid-cols-2 gap-y-4 gap-x-6 my-4'>
          <CustomFormInput
            label={tReservation('detail.funeral_sevice.title')}
            control={control}
            className='col-span-1'
            name={'funeralInfo.funeral'}
            type={InputEnum.OTHER}
            renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
              <CustomSelect
                value={value}
                options={serviceOptions}
                onChange={(selectedValue) => {
                  onChange(selectedValue);
                  handleChangeFuneralService(selectedValue);
                }}
                placeholder={tReservation('detail.placeholder')}
              />
            )}
          />
          <CustomFormInput
            label={tReservation('detail.attendance.title')}
            control={control}
            name={'funeralInfo.attendance'}
            type={InputEnum.SELECT}
            optionItems={ATTENDANCE_OPTIONS}
          />
        </div>
        <div className='flex justify-between items-center'>
          <CustomText variant='label-1-normal' className='!font-semibold'>
            {tReservation('detail.supplies.information')}
          </CustomText>
          {/* <div className='hidden lg:block'>
            <CustomIconButton
              icon={<PlusCircleOutlined className='!text-primary-1000' style={{ fontSize: '18px' }} />}
              onClick={onAddSupply}
            />
          </div> */}
        </div>
        <div>
          <div className='grid grid-cols-9 !bg-neutral-15 lg:py-3 items-center'>
            <CustomText variant='label-1-normal' className='col-span-8 lg:col-span-4 !font-semibold pl-4'>
              {tReservation('detail.supplies.header.type')}
            </CustomText>
            <CustomText variant='label-1-normal' className='col-span-2 !font-semibold pl-4 hidden lg:block'>
              {tReservation('detail.supplies.header.quantity')}
            </CustomText>
            <CustomText variant='label-1-normal' className='col-span-2 !font-semibold pl-4 hidden lg:block'>
              {tReservation('detail.supplies.header.amount')}
            </CustomText>
            {/* <CustomText variant='label-1-normal' className=' col-span-1 !font-semibold pl-4 hidden lg:block'>
              {tReservation('detail.supplies.header.delete')}
            </CustomText> */}

            <div className='flex justify-end mr-2'>
              <CustomIconButton
                icon={<PlusCircleOutlined className='!text-primary-1000' style={{ fontSize: '18px' }} />}
                onClick={onAddSupply}
              />
            </div>
          </div>
          {errors?.serviceInfo?.supplies && <p className='text-red-500 text-sm'>{tForm('require.supplyMaterial')}</p>}
          {fields.map((field, index) => {
            const key = supplyTypes?.[index]?.type as keyof typeof materialGroupTypeNameData;
            const supplyType = materialGroupTypeNameData?.[key];

            return (
              <>
                <div key={field.id} className='grid grid-cols-9 gap-x-[10px] gap-2 lg:gap-4  p-2'>
                  <div className='col-span-9 lg:col-span-4 flex flex-row gap-4'>
                    <div className='grid grid-cols-9 lg:grid-cols-2 gap-x-4 w-full'>
                      <CustomFormInput
                        control={control}
                        name={`serviceInfo.supplies[${index}].type` as any}
                        type={InputEnum.OTHER}
                        renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
                          <CustomSelect
                            value={value}
                            optionsSelect={materialTypeOptions}
                            onChange={(selectedValue) => {
                              onChange(selectedValue);
                              setValue(`serviceInfo.supplies[${index}].kind` as any, null);
                            }}
                            popupMatchSelectWidth={false}
                          />
                        )}
                        className='col-span-3 lg:col-span-1'
                      />

                      <CustomFormInput
                        control={control}
                        name={`serviceInfo.supplies[${index}].kind` as any}
                        type={InputEnum.OTHER}
                        renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
                          <CustomSelect
                            value={value}
                            options={supplyType ? nameIdValueToOptions(supplyType) : []}
                            onChange={(selectedValue) => {
                              onChange(selectedValue);
                              handleChangeMaterial(index, selectedValue);
                            }}
                            popupMatchSelectWidth={false}
                          />
                        )}
                        className='col-span-6 lg:col-span-1'
                      />
                    </div>
                  </div>

                  <div className='col-span-4 flex items-center gap-[10px] lg:col-span-2'>
                    <CustomText variant='label-1-normal' className='col-span-2 block !text-neutral-80 lg:hidden'>
                      {tReservation('detail.supplies.header.quantity')}/{tReservation('detail.supplies.header.amount')}
                    </CustomText>
                    <CustomFormInput
                      control={control}
                      name={`serviceInfo.supplies[${index}].quantity` as any}
                      type={InputEnum.OTHER}
                      allowDecimal={false}
                      renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
                        <CustomNumericFormat
                          value={value}
                          allowNegative={true}
                          onChange={(selectedValue) => {
                            onChange(Number(selectedValue.value));
                            handleChangeQuantity(index, Number(selectedValue.value));
                          }}
                        />
                      )}
                      className='flex-1'
                    />
                  </div>

                  <div className='col-span-5 flex items-center gap-[10px] lg:col-span-3'>
                    <CustomFormInput
                      control={control}
                      className='flex-1'
                      name={`serviceInfo.supplies[${index}].amount` as any}
                      type={InputEnum.THOUSAND_NUMBER}
                      // readOnly={!supplyTypes[index]?.isDefault}
                    />

                    <div className='flex justify-end'>
                      <CustomIconButton icon={<Image src={ICONS.X} alt='delete' />} onClick={() => remove(index)} />
                    </div>
                  </div>
                </div>
                <Divider className='my-4 lg:hidden ' />
              </>
            );
          })}
        </div>
        <FRD_CollapseSelection />
      </div>
    </div>
  );
};
