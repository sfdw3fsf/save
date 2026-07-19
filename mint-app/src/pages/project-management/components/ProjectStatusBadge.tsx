import Badge from '@/components/core/badge/Badge';
import { PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

type ProjectStatusProps = {
  projectStatus: PROJECT_STATUS;
};
const ProjectStatusBadge = ({ projectStatus }: ProjectStatusProps) => {
  const { t: tCommon } = useTranslation('common');
  // THIS IS TEMPORARY REQUEST OF CLIENT => NEED TO FIX IT
  const label = tCommon(`project_management.${projectStatus}`);
  const color = useMemo(() => {
    switch (projectStatus) {
      case PROJECT_STATUS.REQUEST_COMPLETED:
        return 'neutral';
      case PROJECT_STATUS.EXPERT_MATCHING_IN_PROGRESS:
        return 'red';
      case PROJECT_STATUS.UNDER_CONTRACT:
        return 'orange';
      case PROJECT_STATUS.PROJECT_IN_PROGRESS:
        return 'blue';
      case PROJECT_STATUS.PROJECT_COMPLETED:
        return 'green';
      default:
        return 'neutral';
    }
  }, [projectStatus]);
  if (!projectStatus) return;
  return <Badge label={label} color={color} shape='square-round' />;
};

export default ProjectStatusBadge;
