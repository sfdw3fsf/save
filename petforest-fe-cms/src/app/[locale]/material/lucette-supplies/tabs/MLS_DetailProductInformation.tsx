import CustomFormInput from '@/components/common/form/CustomFormField';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useCorrespondent } from '@/hook/correspondent/useCorrespondent';
import useResize from '@/hook/useResize';
import { MaterialDetailInput } from '@/store/material/input/material.input';
import { getMaterialDetailApi, materialLucetteKeys } from '@/store/material/material.api';
import { materialDetailRespToInput } from '@/store/material/material.service';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Col, Row } from 'antd';
import { useEffect, useMemo } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type MLS_DetailProductInformationProps = {
  id: number;
};

const MLS_DetailProductInformation = ({ id }: MLS_DetailProductInformationProps) => {
  const { t: tLucetteSupplies } = useTranslation('material_lucette_supplies');
  const { t: tCommon } = useTranslation('common');
  const { isResponsive } = useResize();
  const { correspondentOptions, correspondentListData } = useCorrespondent();
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_LUCETTE_SUPPLIES);

  const { control, watch, reset, setValue } = useFormContext<MaterialDetailInput>();
  const correspondentId = watch('vendorId');

  const { data: materialDetailData } = useQuery({
    queryKey: materialLucetteKeys.detail(id),
    queryFn: () => getMaterialDetailApi(id),
    select: (response) => {
      return response?.data.data && materialDetailRespToInput(response.data.data, correspondentListData);
    },
    placeholderData: keepPreviousData,
  });

  const colSpan = useMemo(() => {
    return isResponsive ? 24 : 12;
  }, [isResponsive]);

  useEffect(() => {
    if (!correspondentId || !correspondentListData) return;
    const correspondentInformation = correspondentListData.find((item) => item.id === correspondentId);
    if (!correspondentInformation) return;

    setValue('initialVendor', correspondentInformation);
    setValue('vendorManagerName', correspondentInformation?.managerName);
    setValue('vendorContact', correspondentInformation?.contact);
    setValue('vendorDepositAccount', correspondentInformation?.depositAccount);
    setValue('vendorBusinessRegistration', correspondentInformation?.businessRegistration);
  }, [correspondentId]);

  useEffect(() => {
    if (!materialDetailData) return;
    reset(materialDetailData);
  }, [materialDetailData]);

  return (
    <div className='mt-4'>
      <Row gutter={[24, 16]}>
        <Col span={colSpan}>
          <CustomFormInput
            readOnly={!canEdit}
            control={control}
            name='price'
            type={InputEnum.THOUSAND_NUMBER}
            label={tLucetteSupplies('detail.unit_price')}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            disabled={!canEdit}
            control={control}
            optionItems={correspondentOptions}
            name='vendorId'
            type={InputEnum.SELECT}
            label={tLucetteSupplies('detail.business_partner')}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            readOnly={!canEdit}
            control={control}
            name='vendorManagerName'
            type={InputEnum.TEXT}
            label={tLucetteSupplies('detail.business_partnerManager')}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            readOnly={!canEdit}
            control={control}
            name='vendorContact'
            type={InputEnum.TEXT}
            label={tLucetteSupplies('detail.contact_number')}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            readOnly={!canEdit}
            control={control}
            name='vendorDepositAccount'
            type={InputEnum.TEXT}
            label={tLucetteSupplies('detail.deposit_account')}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            readOnly={!canEdit}
            control={control}
            name='vendorBusinessRegistration'
            type={InputEnum.TEXT}
            label={tLucetteSupplies('detail.business_registration_number')}
          />
        </Col>
        <Col span={24}>
          <CustomFormInput
            readOnly={!canEdit}
            control={control}
            name='note'
            type={InputEnum.TEXT_AREA}
            label={tCommon('memo')}
            classNameInput='!h-[120px]'
            isResize={false}
          />
        </Col>
      </Row>
    </div>
  );
};

export default MLS_DetailProductInformation;
