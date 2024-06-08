import React, { useState } from "react";
import { ProjectResponse, TaskResponse } from "../../models/models";
import axiosInstance from "../../utils/axiosInterceptors";
import { Button, Modal, Form, Card, Row, Col } from "react-bootstrap";
import TaskForm from "../TaskForm/TaskForm";
import "./projectList.css";

interface ProjectListProps {
  projects: ProjectResponse[];
  tasks: TaskResponse[];
  expandedProjectId: number;
  handleExpandClick: (projectId: number) => void;
  fetchTasks: (projectId: number, status?: string) => void;
}

const ProjectList: React.FC<ProjectListProps> = ({
  projects,
  tasks,
  expandedProjectId,
  handleExpandClick,
  fetchTasks,
}) => {
  const [showEditModal, setShowEditModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [selectedTask, setSelectedTask] = useState<TaskResponse | null>(null);
  const [selectedProjectId, setSelectedProjectId] = useState<number | null>(
    null
  );
  const [selectedStatus, setSelectedStatus] = useState<string>("");

  const handleCloseEditModal = () => {
    setShowEditModal(false);
    setSelectedTask(null);
  };

  const handleCloseAddModal = () => {
    setShowAddModal(false);
    setSelectedProjectId(null);
  };

  const handleShowEditModal = (task: TaskResponse) => {
    setSelectedTask(task);
    setShowEditModal(true);
  };

  const handleShowAddModal = (projectId: number) => {
    setSelectedProjectId(projectId);
    setShowAddModal(true);
  };

  const handleDeleteTask = async (taskId: number) => {
    try {
      await axiosInstance.delete(`/tasks/${taskId}`);
      fetchTasks(expandedProjectId, selectedStatus);
    } catch (error) {
      console.error("Görev silinirken hata oluştu:", error);
    }
  };

  const handleStatusChange = (status: string) => {
    setSelectedStatus(status);
    fetchTasks(expandedProjectId, status);
  };

  return (
    <div className="project-list">
      {projects &&
        projects.map((project) => (
          <Card key={project.id} className="mb-3 project-card">
            <Card.Body>
              <Card.Title className="project-title">
              <strong>Proje Adı:</strong>{" "}
                {project.projectName}</Card.Title>
              <Card.Text className="project-dates">
                <strong>Başlangıç Tarihi:</strong>{" "}
                {new Date(project.startDate).toLocaleDateString()}
              </Card.Text>
              <Card.Text className="project-dates">
                <strong>Bitiş Tarihi:</strong>{" "}
                {new Date(project.endDate).toLocaleDateString()}
              </Card.Text>
              <Button
                variant="secondary"
                className="mb-2"
                onClick={() => handleExpandClick(project.id)}
              >
                {expandedProjectId === project.id ? "Gizle" : "Görevleri Göster"}
              </Button>
              {expandedProjectId === project.id && (
                <div className="expanded-section">
                  <Row className="mb-2 align-items-center">
                    <Col>
                      <Button
                        variant="primary"
                        onClick={() => handleShowAddModal(project.id)}
                      >
                        Görev Ekle
                      </Button>
                    </Col>
                    <Col>
                      <Form.Group controlId="statusSelect" className="mb-0">
                        <Form.Label>Görev Durumu</Form.Label>
                        <Form.Control
                          as="select"
                          value={selectedStatus}
                          onChange={(e) => handleStatusChange(e.target.value)}
                        >
                          <option value="">Tümü</option>
                          <option value="NEW">Yapılacak</option>
                          <option value="IN_PROGRESS">Devam Ediyor</option>
                          <option value="COMPLETED">Tamamlandı</option>
                        </Form.Control>
                      </Form.Group>
                    </Col>
                  </Row>
                  <Card className="tasks-card">
                    <Card.Body>
                      <h3 className="task-header">Görevler</h3>
                      <ul className="list-group list-group-flush">
                        {tasks &&
                          tasks.map((task) => (
                            <li key={task.id} className="list-group-item">
                              <h6>
                                <strong>Görev Başlığı:</strong> {task.title}
                              </h6>
                              <p>
                                <strong>Görev Açıklaması:</strong> {task.description}
                              </p>
                              <p>
                                <strong>Oluşturulma Tarihi:</strong>{" "}
                                {new Date(task.createdDate).toLocaleDateString()}
                              </p>
                              <p>
                                <strong>Durum:</strong> {task.status}
                              </p>
                              <Button
                                variant="primary"
                                className="mr-2"
                                onClick={() => handleShowEditModal(task)}
                              >
                                Düzenle
                              </Button>
                              <Button
                                variant="danger"
                                onClick={() => handleDeleteTask(task.id)}
                              >
                                Sil
                              </Button>
                            </li>
                          ))}
                      </ul>
                    </Card.Body>
                  </Card>
                </div>
              )}
            </Card.Body>
          </Card>
        ))}

      {/* Edit Task Modal */}
      {selectedTask && (
        <Modal show={showEditModal} onHide={handleCloseEditModal}>
          <Modal.Header closeButton>
            <Modal.Title>Görevi Düzenle</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <TaskForm
              fetchTasks={() => fetchTasks(expandedProjectId, selectedStatus)}
              taskId={selectedTask.id}
              projectId={expandedProjectId}
              closeModal={handleCloseEditModal}
            />
          </Modal.Body>
        </Modal>
      )}

      {/* Add Task Modal */}
      {selectedProjectId !== null && (
        <Modal show={showAddModal} onHide={handleCloseAddModal}>
          <Modal.Header closeButton>
            <Modal.Title>Görev Ekle</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <TaskForm
              fetchTasks={() => fetchTasks(expandedProjectId, selectedStatus)}
              projectId={expandedProjectId}
              closeModal={handleCloseAddModal}
            />
          </Modal.Body>
        </Modal>
      )}
    </div>
  );
};

export default ProjectList;


